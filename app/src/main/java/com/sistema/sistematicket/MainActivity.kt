package com.sistema.sistematicket

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.sistema.sistematicket.data.AppDatabase
import com.sistema.sistematicket.ui.theme.LoginScreen
import com.sistema.sistematicket.ui.theme.MarcelaPazTicketTheme
import com.sistema.sistematicket.ui.theme.abrir
import com.sistema.sistematicket.utils.Sesion

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Si ya hay una sesion iniciada la envia directamente al inico
        if (Sesion.obtener(this) != -1) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
            return
        }

        enableEdgeToEdge()
        val dao = AppDatabase.obtener(this).usuarioDao()

        setContent {
            MarcelaPazTicketTheme {
                Scaffold { padding ->
                    Box(Modifier.padding(padding)) {
                        LoginScreen(
                            dao = dao,
                            onLoginExitoso = { usuario ->
                                // Guarda la sesión y abre el inicio
                                Sesion.guardar(this@MainActivity, usuario.id)
                                abrir(this@MainActivity, HomeActivity::class.java)
                                finish()
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