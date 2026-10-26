package com.sistema.sistematicket.ui.theme

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.sistema.sistematicket.data.DocumentoDAO
import com.sistema.sistematicket.model.Documento
import com.sistema.sistematicket.utils.nombreArchivo
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DocumentosScreen(dao: DocumentoDAO, usuarioId: Int) {
    val contexto = LocalContext.current
    val scope = rememberCoroutineScope()

    var documentos by remember { mutableStateOf(listOf<Documento>()) }
    var porEliminar by remember { mutableStateOf<Documento?>(null) }

    // Carga los documentos del usuario al abrir la pantalla
    LaunchedEffect(Unit) {
        documentos = dao.listar(usuarioId)
    }

    // selector de archivos del sistema (PDF y Word)
    val selector = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        if (uri != null) {
            // Guarda el permiso para poder abrir el archivo después.
            try {
                contexto.contentResolver.takePersistableUriPermission(
                    uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (e: SecurityException) {
                // Si no se puede guardar el permiso, se sigue igual.
            }

            val mime = contexto.contentResolver.getType(uri) ?: ""
            val tipo = if (mime == "application/pdf") "PDF" else "Word"

            scope.launch {
                // Guarda el documento en SQLite y actualiza la lista.
                dao.insertar(
                    Documento(
                        usuarioId = usuarioId,
                        nombre = nombreArchivo(contexto, uri),
                        uri = uri.toString(),
                        tipo = tipo,
                        mime = mime,
                        fecha = System.currentTimeMillis()
                    )
                )
                documentos = dao.listar(usuarioId)
                Toast.makeText(contexto, "Documento cargado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(
            onClick = {
                selector.launch(
                    arrayOf(
                        "application/pdf",
                        "application/msword",
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
                    )
                )
            },
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) {
            Icon(Icons.Filled.UploadFile, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Cargar documento (PDF o Word)")
        }

        if (documentos.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Aún no has cargado documentos")
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(documentos) { doc ->
                    TarjetaDocumento(
                        doc = doc,
                        onVer = { abrirDocumento(contexto, doc) },
                        onEliminar = { porEliminar = doc }
                    )
                }
            }
        }
    }

    // Mensaje de verificacion antes de eliminar
    val docAEliminar = porEliminar
    if (docAEliminar != null) {
        AlertDialog(
            onDismissRequest = { porEliminar = null },
            title = { Text("Eliminar documento") },
            text = { Text("¿Seguro que quieres eliminar \"${docAEliminar.nombre}\"?") },
            confirmButton = {
                TextButton(onClick = {
                    scope.launch {
                        dao.eliminar(docAEliminar)
                        documentos = dao.listar(usuarioId)
                        porEliminar = null
                    }
                }) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { porEliminar = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun TarjetaDocumento(doc: Documento, onVer: () -> Unit, onEliminar: () -> Unit) {
    val formato = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale("es", "CL"))

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(Icons.Filled.Description, contentDescription = null)
                Column {
                    Text(doc.nombre, style = MaterialTheme.typography.titleMedium, maxLines = 1)
                    Text("${doc.tipo} - ${formato.format(Date(doc.fecha))}")
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = onVer) {
                    Icon(Icons.Filled.Visibility, contentDescription = null)
                    Spacer(Modifier.width(6.dp))
                    Text("Ver")
                }
                OutlinedButton(onClick = onEliminar) {
                    Icon(Icons.Filled.Delete, contentDescription = null)
                    Spacer(Modifier.width(6.dp))
                    Text("Eliminar")
                }
            }
        }
    }
}

// Abre el documento con otra app del teléfono
fun abrirDocumento(contexto: android.content.Context, doc: Documento) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.setDataAndType(Uri.parse(doc.uri), doc.mime)
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    try {
        contexto.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(contexto, "No cuentas con una app para abrir este tipo de archivo panita iii", Toast.LENGTH_LONG).show()
    }
}