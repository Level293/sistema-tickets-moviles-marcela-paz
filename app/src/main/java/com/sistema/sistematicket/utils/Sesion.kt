package com.sistema.sistematicket.utils

import android.content.Context

// Guarda el id del usuario que inició sesión.
object Sesion {

    fun guardar(contexto: Context, id: Int) {
        contexto.getSharedPreferences("sesion", Context.MODE_PRIVATE)
            .edit().putInt("usuario_id", id).apply()
    }

    // Devuelve -1 si no hay una sesión iniciada
    fun obtener(contexto: Context): Int {
        return contexto.getSharedPreferences("sesion", Context.MODE_PRIVATE)
            .getInt("usuario_id", -1)
    }

    fun cerrar(contexto: Context) {
        contexto.getSharedPreferences("sesion", Context.MODE_PRIVATE)
            .edit().remove("usuario_id").apply()
    }
}