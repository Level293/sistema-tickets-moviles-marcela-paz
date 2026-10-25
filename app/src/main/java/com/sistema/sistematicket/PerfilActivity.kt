package com.sistema.sistematicket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.sistema.sistematicket.data.AppDatabase
import com.sistema.sistematicket.model.Usuario
import com.sistema.sistematicket.ui.theme.MarcelaPazTicketTheme
import com.sistema.sistematicket.ui.theme.PantallaBase
import com.sistema.sistematicket.ui.theme.PerfilScreen
import com.sistema.sistematicket.ui.theme.cerrarSesion
import com.sistema.sistematicket.utils.Sesion

class PerfilActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val dao = AppDatabase.obtener(this).usuarioDao()

        setContent {
            MarcelaPazTicketTheme {
                var usuario by remember { mutableStateOf<Usuario?>(null) }

                LaunchedEffect(Unit) {
                    usuario = dao.buscarPorId(Sesion.obtener(this@PerfilActivity))
                }

                PantallaBase("Perfil", PerfilActivity::class.java) { padding ->
                    Box(Modifier.padding(padding)) {
                        PerfilScreen(
                            usuario = usuario,
                            onCerrarSesion = { cerrarSesion(this@PerfilActivity) }
                        )
                    }
                }
            }
        }
    }
}