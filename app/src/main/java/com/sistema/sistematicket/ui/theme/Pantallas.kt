package com.sistema.sistematicket.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.sistema.sistematicket.R

// ---------- Login ----------
@Composable
fun LoginScreen(onIngresar: () -> Unit, onIrRegistro: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.img),
            contentDescription = "Logo",
            modifier = Modifier.size(150.dp)
        )
        Text("Marcela Paz Ticket", style = MaterialTheme.typography.headlineMedium)
        Text("Pantalla de inicio de sesión")

        Button(
            onClick = onIngresar,
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) {
            Text("Iniciar sesión")
        }
        TextButton(onClick = onIrRegistro) {
            Text("¿No tienes cuenta? Regístrate")
        }
    }
}

// ---------- Registro ----------
@Composable
fun RegistroScreen(onVolver: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Crear cuenta", style = MaterialTheme.typography.headlineMedium)
        Text("Pantalla de registro de usuarios")

        Button(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) {
            Text("Volver al inicio de sesión")
        }
    }
}

// ---------- Inicio ----------
@Composable
fun HomeScreen(onDocumentos: () -> Unit, onPerfil: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Bienvenido", style = MaterialTheme.typography.headlineSmall)
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

// ---------- Documentos (se completa en v3) ----------
@Composable
fun DocumentosScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Aquí irán los documentos")
    }
}

// ---------- Perfil ----------
@Composable
fun PerfilScreen(onCerrarSesion: () -> Unit) {
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
        Text("Mi perfil", style = MaterialTheme.typography.headlineSmall)
        Text("Los datos del usuario se muestran desde v2")

        Button(
            onClick = onCerrarSesion,
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) {
            Text("Cerrar sesión")
        }
    }
}