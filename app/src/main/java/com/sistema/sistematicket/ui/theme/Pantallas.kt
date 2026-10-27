package com.sistema.sistematicket.ui.theme

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.sistema.sistematicket.R
import com.sistema.sistematicket.data.UsuarioDAO
import com.sistema.sistematicket.model.Usuario
import com.sistema.sistematicket.utils.Validators
import kotlinx.coroutines.launch

// Muestra ícono, mensaje de error e ícono de error
@Composable
fun CampoTexto(
    valor: String,
    onCambio: (String) -> Unit,
    etiqueta: String,
    icono: ImageVector,
    error: String?,
    esClave: Boolean = false,
    teclado: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onCambio,
        label = { Text(etiqueta) },
        leadingIcon = { Icon(icono, contentDescription = null) },
        trailingIcon = {
            if (error != null) {
                Icon(
                    Icons.Filled.Error,
                    contentDescription = "Error",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        },
        isError = error != null,
        supportingText = { if (error != null) Text(error) },
        singleLine = true,
        visualTransformation = if (esClave) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = if (esClave) KeyboardType.Password else teclado),
        modifier = Modifier.fillMaxWidth()
    )
}

// Login
@Composable
fun LoginScreen(
    dao: UsuarioDAO,
    onLoginExitoso: (Usuario) -> Unit,
    onIrRegistro: () -> Unit
) {
    val scope = rememberCoroutineScope()

    var correo by rememberSaveable { mutableStateOf("") }
    var clave by rememberSaveable { mutableStateOf("") }
    var errorCorreo by remember { mutableStateOf<String?>(null) }
    var errorClave by remember { mutableStateOf<String?>(null) }
    var mensaje by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo del colegio
        Image(
            painter = painterResource(id = R.drawable.img),
            contentDescription = "Logo",
            modifier = Modifier.size(120.dp)
        )
        Text("Marcela Paz Ticket", style = MaterialTheme.typography.headlineMedium)
        Text("Inicia sesión para continuar")
        Spacer(Modifier.height(16.dp))

        CampoTexto(
            valor = correo,
            onCambio = { correo = it; errorCorreo = Validators.correo(it); mensaje = "" },
            etiqueta = "Correo",
            icono = Icons.Filled.Email,
            error = errorCorreo,
            teclado = KeyboardType.Email
        )
        CampoTexto(
            valor = clave,
            onCambio = { clave = it; errorClave = Validators.requerido(it, "La contraseña"); mensaje = "" },
            etiqueta = "Contraseña",
            icono = Icons.Filled.Lock,
            error = errorClave,
            esClave = true
        )

        if (mensaje.isNotEmpty()) {
            Text(mensaje, color = MaterialTheme.colorScheme.error)
        }

        Button(
            onClick = {
                errorCorreo = Validators.correo(correo)
                errorClave = Validators.requerido(clave, "La contraseña")

                if (errorCorreo == null && errorClave == null) {
                    // Corrutina para no bloquear la interfaz.
                    scope.launch {
                        val usuario = dao.buscarPorCorreo(correo.trim().lowercase())
                        if (usuario != null && usuario.clave == clave) {
                            onLoginExitoso(usuario)
                        } else {
                            mensaje = "Correo o contraseña incorrectos"
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) {
            Text("Iniciar sesión")
        }
        TextButton(onClick = onIrRegistro) {
            Text("¿No tienes cuenta? Regístrate")
        }
    }
}

// Registro
@Composable
fun RegistroScreen(dao: UsuarioDAO, onVolver: () -> Unit) {
    val scope = rememberCoroutineScope()
    val contexto = LocalContext.current

    var nombre by rememberSaveable { mutableStateOf("") }
    var correo by rememberSaveable { mutableStateOf("") }
    var clave by rememberSaveable { mutableStateOf("") }
    var confirmar by rememberSaveable { mutableStateOf("") }

    var errorNombre by remember { mutableStateOf<String?>(null) }
    var errorCorreo by remember { mutableStateOf<String?>(null) }
    var errorClave by remember { mutableStateOf<String?>(null) }
    var errorConfirmar by remember { mutableStateOf<String?>(null) }
    var mensaje by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Crear cuenta", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(8.dp))

        CampoTexto(
            valor = nombre,
            onCambio = { nombre = it; errorNombre = Validators.nombre(it) },
            etiqueta = "Nombre completo",
            icono = Icons.Filled.Person,
            error = errorNombre
        )
        CampoTexto(
            valor = correo,
            onCambio = { correo = it; errorCorreo = Validators.correo(it); mensaje = "" },
            etiqueta = "Correo",
            icono = Icons.Filled.Email,
            error = errorCorreo,
            teclado = KeyboardType.Email
        )
        CampoTexto(
            valor = clave,
            onCambio = {
                clave = it
                errorClave = Validators.clave(it)
                if (confirmar.isNotEmpty()) errorConfirmar = Validators.confirmar(it, confirmar)
            },
            etiqueta = "Contraseña",
            icono = Icons.Filled.Lock,
            error = errorClave,
            esClave = true
        )
        CampoTexto(
            valor = confirmar,
            onCambio = { confirmar = it; errorConfirmar = Validators.confirmar(clave, it) },
            etiqueta = "Confirmar contraseña",
            icono = Icons.Filled.Lock,
            error = errorConfirmar,
            esClave = true
        )

        if (mensaje.isNotEmpty()) {
            Text(mensaje, color = MaterialTheme.colorScheme.error)
        }

        Button(
            onClick = {
                errorNombre = Validators.nombre(nombre)
                errorCorreo = Validators.correo(correo)
                errorClave = Validators.clave(clave)
                errorConfirmar = Validators.confirmar(clave, confirmar)

                val hayErrores = errorNombre != null || errorCorreo != null ||
                        errorClave != null || errorConfirmar != null

                if (!hayErrores) {
                    scope.launch {
                        val correoLimpio = correo.trim().lowercase()
                        if (dao.buscarPorCorreo(correoLimpio) != null) {
                            mensaje = "Ese correo ya está registrado"
                        } else {
                            // Inserta el usuario en SQLite.
                            dao.insertar(
                                Usuario(nombre = nombre.trim(), correo = correoLimpio, clave = clave)
                            )
                            Toast.makeText(contexto, "Cuenta creada. Inicia sesión", Toast.LENGTH_SHORT).show()
                            onVolver()
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) {
            Text("Registrarme")
        }
        TextButton(onClick = onVolver) {
            Text("¿Ya tienes cuenta? Inicia sesión")
        }
    }
}

// Inicio
@Composable
fun HomeScreen(nombre: String, onDocumentos: () -> Unit, onPerfil: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Hola, $nombre", style = MaterialTheme.typography.headlineSmall)
        Text("¿Qué quieres hacer hoy?")

        TarjetaAcceso(
            titulo = "Mis documentos",
            detalle = "Carga, visualiza y elimina archivos",
            icono = Icons.Filled.Description,
            onClick = onDocumentos
        )
        TarjetaAcceso(
            titulo = "Mi perfil",
            detalle = "Revisa tus datos y cierra sesión",
            icono = Icons.Filled.Person,
            onClick = onPerfil
        )
    }
}

@Composable
fun TarjetaAcceso(titulo: String, detalle: String, icono: ImageVector, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(icono, contentDescription = null, modifier = Modifier.size(40.dp))
            Column {
                Text(titulo, style = MaterialTheme.typography.titleMedium)
                Text(detalle)
            }
        }
    }
}

// Perfil
@Composable
fun PerfilScreen(usuario: Usuario?, onCerrarSesion: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically)
    ) {
        Icon(
            Icons.Filled.AccountCircle,
            contentDescription = null,
            modifier = Modifier.size(96.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Text(usuario?.nombre ?: "", style = MaterialTheme.typography.headlineSmall)
        Text(usuario?.correo ?: "")
        Spacer(Modifier.height(16.dp))

        Button(
            onClick = onCerrarSesion,
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) {
            Text("Cerrar sesión")
        }
    }
}