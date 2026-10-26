package com.sistema.sistematicket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import com.sistema.sistematicket.data.AppDatabase
import com.sistema.sistematicket.ui.theme.DocumentosScreen
import com.sistema.sistematicket.ui.theme.MarcelaPazTicketTheme
import com.sistema.sistematicket.ui.theme.PantallaBase
import com.sistema.sistematicket.utils.Sesion

class DocumentosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val dao = AppDatabase.obtener(this).documentoDao()
        val usuarioId = Sesion.obtener(this)

        setContent {
            MarcelaPazTicketTheme {
                PantallaBase("Documentos", DocumentosActivity::class.java) { padding ->
                    Box(Modifier.padding(padding)) {
                        DocumentosScreen(dao = dao, usuarioId = usuarioId)
                    }
                }
            }
        }
    }
}