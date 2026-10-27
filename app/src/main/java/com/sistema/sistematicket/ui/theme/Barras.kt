package com.sistema.sistematicket.ui.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import com.sistema.sistematicket.DocumentosActivity
import com.sistema.sistematicket.HomeActivity
import com.sistema.sistematicket.PerfilActivity

// Cada opción del menú apunta a una Activity
data class ItemNav(val titulo: String, val icono: ImageVector, val destino: Class<*>)

val itemsNav = listOf(
    ItemNav("Inicio", Icons.Filled.Home, HomeActivity::class.java),
    ItemNav("Documentos", Icons.Filled.Description, DocumentosActivity::class.java),
    ItemNav("Perfil", Icons.Filled.Person, PerfilActivity::class.java)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaBase(
    titulo: String,
    actual: Class<*>,
    contenido: @Composable (PaddingValues) -> Unit
) {
    val contexto = LocalContext.current
    var menuAbierto by remember { mutableStateOf(false) }
    var confirmarSalida by remember { mutableStateOf(false) }

    Scaffold(
        // Barra superior (como el ejemplo CenterAlignedTopAppBar del profesor).
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(titulo, maxLines = 1, overflow = TextOverflow.Ellipsis)
                },
                actions = {
                    Box {
                        IconButton(onClick = { menuAbierto = true }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Menú"
                            )
                        }
                        // Menú desplegable con las pantallas y cerrar sesión.
                        DropdownMenu(
                            expanded = menuAbierto,
                            onDismissRequest = { menuAbierto = false }
                        ) {
                            itemsNav.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(item.titulo) },
                                    onClick = {
                                        menuAbierto = false
                                        if (item.destino != actual) {
                                            abrir(contexto, item.destino, true)
                                        }
                                    }
                                )
                            }
                            DropdownMenuItem(
                                text = { Text("Cerrar sesión") },
                                onClick = {
                                    menuAbierto = false
                                    confirmarSalida = true
                                }
                            )
                        }
                    }
                }
            )
        },
        // Barra de navegación inferior.
        bottomBar = {
            NavigationBar {
                itemsNav.forEach { item ->
                    NavigationBarItem(
                        selected = item.destino == actual,
                        onClick = {
                            if (item.destino != actual) {
                                abrir(contexto, item.destino, true)
                            }
                        },
                        icon = { Icon(item.icono, contentDescription = item.titulo) },
                        label = { Text(item.titulo) }
                    )
                }
            }
        }
    ) { padding ->
        contenido(padding)
    }

    // Mensaje de confirmación antes de cerrar sesión.
    if (confirmarSalida) {
        AlertDialog(
            onDismissRequest = { confirmarSalida = false },
            title = { Text("Cerrar sesión") },
            text = { Text("¿Seguro que quieres cerrar tu sesión?") },
            confirmButton = {
                TextButton(onClick = {
                    confirmarSalida = false
                    cerrarSesion(contexto)
                }) {
                    Text("Cerrar sesión")
                }
            },
            dismissButton = {
                TextButton(onClick = { confirmarSalida = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}