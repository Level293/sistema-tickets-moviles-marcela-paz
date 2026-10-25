package com.sistema.sistematicket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import com.sistema.sistematicket.ui.theme.HomeScreen
import com.sistema.sistematicket.ui.theme.MarcelaPazTicketTheme
import com.sistema.sistematicket.ui.theme.PantallaBase
import com.sistema.sistematicket.ui.theme.abrir

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MarcelaPazTicketTheme {
                PantallaBase("Inicio", HomeActivity::class.java) { padding ->
                    Box(Modifier.padding(padding)) {
                        HomeScreen(
                            onDocumentos = {
                                abrir(this@HomeActivity, DocumentosActivity::class.java, true)
                            },
                            onPerfil = {
                                abrir(this@HomeActivity, PerfilActivity::class.java, true)
                            }
                        )
                    }
                }
            }
        }
    }
}