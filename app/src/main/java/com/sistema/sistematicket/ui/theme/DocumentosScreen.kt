package com.sistema.sistematicket.ui.theme

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.sistema.sistematicket.data.DocumentoDAO
import com.sistema.sistematicket.model.Documento
import com.sistema.sistematicket.utils.crearArchivoFoto
import com.sistema.sistematicket.utils.eliminarFoto
import com.sistema.sistematicket.utils.nombreArchivo
import com.sistema.sistematicket.utils.uriDeArchivo
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DocumentosScreen(dao: DocumentoDAO, usuarioId: Int) {
    val contexto = LocalContext.current
    val scope = rememberCoroutineScope()

    var documentos by remember { mutableStateOf(listOf<Documento>()) }
    var porEliminar by remember { mutableStateOf<Documento?>(null) }

    // Ruta de la foto que se está tomando
    var rutaFoto by rememberSaveable { mutableStateOf("") }

    // Carga los documentos del usuario al abrir la pantalla
    LaunchedEffect(Unit) {
        documentos = dao.listar(usuarioId)
    }

    // selector de archivos del sistema PDF y Word
    val selector = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        if (uri != null) {
            // Guarda el permiso para poder abrir el archivo después
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

    // cámara del teléfono.
    val camara = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { exito ->
        if (rutaFoto.isNotEmpty()) {
            val archivo = File(rutaFoto)
            if (exito) {
                scope.launch {
                    dao.insertar(
                        Documento(
                            usuarioId = usuarioId,
                            nombre = archivo.name,
                            uri = uriDeArchivo(contexto, archivo).toString(),
                            tipo = "Foto",
                            mime = "image/jpeg",
                            fecha = System.currentTimeMillis()
                        )
                    )
                    documentos = dao.listar(usuarioId)
                    Toast.makeText(contexto, "Foto guardada", Toast.LENGTH_SHORT).show()
                }
            } else {
                archivo.delete()
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
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
                modifier = Modifier.weight(1f).height(52.dp)
            ) {
                Icon(Icons.Filled.UploadFile, contentDescription = null)
                Spacer(Modifier.width(6.dp))
                Text("Cargar")
            }

            FilledTonalButton(
                onClick = {
                    // Crea el archivo y abre la cámara para llenarlo
                    val archivo = crearArchivoFoto(contexto)
                    rutaFoto = archivo.absolutePath
                    camara.launch(uriDeArchivo(contexto, archivo))
                },
                modifier = Modifier.weight(1f).height(52.dp)
            ) {
                Icon(Icons.Filled.PhotoCamera, contentDescription = null)
                Spacer(Modifier.width(6.dp))
                Text("Foto")
            }
        }

        if (documentos.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Aún no has subido ningun documento")
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

    // mensaje de confirmacion antes de eliminar un archivo
    val docAEliminar = porEliminar
    if (docAEliminar != null) {
        AlertDialog(
            onDismissRequest = { porEliminar = null },
            title = { Text("Eliminar") },
            text = { Text("¿Seguro que quieres eliminar \"${docAEliminar.nombre}\"?") },
            confirmButton = {
                TextButton(onClick = {
                    scope.launch {
                        dao.eliminar(docAEliminar)
                        if (docAEliminar.tipo == "Foto") {
                            eliminarFoto(contexto, docAEliminar.nombre)
                        }
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

    // Al tocar la tarjeta se expande o se contrae
    var expandida by remember { mutableStateOf(false) }

    // el color cambia suavemente
    val colorFondo by animateColorAsState(
        targetValue = if (expandida) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            MaterialTheme.colorScheme.surfaceVariant
        },
        animationSpec = tween(durationMillis = 500),
        label = "Color de la tarjeta"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .clickable { expandida = !expandida },
        colors = CardDefaults.cardColors(containerColor = colorFondo)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    if (doc.tipo == "Foto") Icons.Filled.PhotoCamera else Icons.Filled.Description,
                    contentDescription = null
                )
                Column {
                    Text(doc.nombre, style = MaterialTheme.typography.titleMedium, maxLines = 1)
                    Text(doc.tipo)
                }
            }

            // los detalles se despliegan
            AnimatedVisibility(
                visible = expandida,
                enter = expandVertically(
                    animationSpec = tween(600),
                    expandFrom = Alignment.Top
                ),
                exit = shrinkVertically(
                    animationSpec = tween(600),
                    shrinkTowards = Alignment.Top
                )
            ) {
                Column(
                    modifier = Modifier.padding(top = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Fecha: ${formato.format(Date(doc.fecha))}")
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
    }
}

// Abre el archivo o la foto con otra app del teléfono
fun abrirDocumento(contexto: android.content.Context, doc: Documento) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.setDataAndType(Uri.parse(doc.uri), doc.mime)
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    try {
        contexto.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(contexto, "No hay una app para abrir este archivo", Toast.LENGTH_LONG).show()
    }
}