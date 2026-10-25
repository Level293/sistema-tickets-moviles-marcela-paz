package com.sistema.sistematicket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.sistema.sistematicket.ui.theme.LoginScreen
import com.sistema.sistematicket.ui.theme.MarcelaPazTicketTheme
import com.sistema.sistematicket.ui.theme.abrir

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MarcelaPazTicketTheme {
                Scaffold { padding ->
                    Box(Modifier.padding(padding)) {
                        LoginScreen(
                            onIngresar = {
                                abrir(this@MainActivity, HomeActivity::class.java)
                                finish() // cierra el login para que "atrás" no vuelva
                            },
                            onIrRegistro = {
                                abrir(this@MainActivity, RegistroActivity::class.java)
                            }
                        )
                    }
                }
            }
        }
    }
}