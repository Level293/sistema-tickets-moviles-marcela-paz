package com.sistema.sistematicket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import com.sistema.sistematicket.ui.theme.MarcelaPazTicketTheme
import com.sistema.sistematicket.ui.theme.PantallaBase
import com.sistema.sistematicket.ui.theme.PerfilScreen
import com.sistema.sistematicket.ui.theme.cerrarSesion

class PerfilActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MarcelaPazTicketTheme {
                PantallaBase("Perfil", PerfilActivity::class.java) { padding ->
                    Box(Modifier.padding(padding)) {
                        PerfilScreen(onCerrarSesion = { cerrarSesion(this@PerfilActivity) })
                    }
                }
            }
        }
    }
}