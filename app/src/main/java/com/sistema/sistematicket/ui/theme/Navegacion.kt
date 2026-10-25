package com.sistema.sistematicket.ui.theme

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import com.sistema.sistematicket.MainActivity
import com.sistema.sistematicket.utils.Sesion

fun abrir(contexto: Context, destino: Class<*>, alFrente: Boolean = false) {
    val intent = Intent(contexto, destino)
    if (alFrente) {
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
    }

    val opciones = ActivityOptions.makeCustomAnimation(
        contexto,
        android.R.anim.fade_in,
        android.R.anim.fade_out
    )
    contexto.startActivity(intent, opciones.toBundle())
}

// Borra la sesion y devuelve al login
fun cerrarSesion(contexto: Context) {
    Sesion.cerrar(contexto)

    val intent = Intent(contexto, MainActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    contexto.startActivity(intent)
}