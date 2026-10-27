package com.sistema.sistematicket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.sistema.sistematicket.data.AppDatabase
import com.sistema.sistematicket.ui.theme.MarcelaPazTicketTheme
import com.sistema.sistematicket.ui.theme.RegistroScreen

class RegistroActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val dao = AppDatabase.obtener(this).usuarioDao()

        setContent {
            MarcelaPazTicketTheme {
                Scaffold { padding ->
                    Box(Modifier.padding(padding)) {
                        RegistroScreen(dao = dao, onVolver = { finish() })
                    }
                }
            }
        }
    }
}