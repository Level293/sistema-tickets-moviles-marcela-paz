package com.sistema.sistematicket.ui.theme

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import com.sistema.sistematicket.MainActivity

// Abre otra Activity con animación (igual que el ejemplo del profesor).
fun abrir(contexto: Context, destino: Class<*>, alFrente: Boolean = false) {
    val intent = Intent(contexto, destino)

    // Si la pantalla ya está abierta, la trae al frente en vez de crear otra.
    if (alFrente) {
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
    }

    // La nueva pantalla aparece y la actual se desvanece.
    val opciones = ActivityOptions.makeCustomAnimation(
        contexto,
        android.R.anim.fade_in,
        android.R.anim.fade_out
    )
    contexto.startActivity(intent, opciones.toBundle())
}

// Vuelve al login cerrando todas las pantallas anteriores.
fun cerrarSesion(contexto: Context) {
    val intent = Intent(contexto, MainActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    contexto.startActivity(intent)
}