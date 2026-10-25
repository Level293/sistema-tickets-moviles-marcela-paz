package com.sistema.sistematicket.utils

import android.util.Patterns

// Cada función devuelve null si el dato es válido, o el mensaje de error.
object Validators {

    fun requerido(texto: String, campo: String): String? {
        if (texto.isBlank()) return "$campo es obligatorio"
        return null
    }

    fun nombre(texto: String): String? {
        if (texto.isBlank()) return "El nombre es obligatorio"
        if (texto.trim().length < 3) return "Mínimo 3 caracteres"
        return null
    }

    fun correo(texto: String): String? {
        if (texto.isBlank()) return "El correo es obligatorio"
        if (!Patterns.EMAIL_ADDRESS.matcher(texto.trim()).matches()) return "Correo no válido"
        return null
    }

    fun clave(texto: String): String? {
        if (texto.length < 8) return "Mínimo 8 caracteres"
        if (!texto.any { it.isDigit() }) return "Debe incluir un número"
        if (!texto.any { it.isUpperCase() }) return "Debe incluir una mayúscula"
        return null
    }

    fun confirmar(clave: String, confirmacion: String): String? {
        if (confirmacion.isBlank()) return "Confirma tu contraseña"
        if (clave != confirmacion) return "Las contraseñas no coinciden"
        return null
    }
}