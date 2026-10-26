package com.sistema.sistematicket.utils

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns

// Obtiene el nombre real del archivo elegido por el usuario
fun nombreArchivo(contexto: Context, uri: Uri): String {
    var nombre = "documento"
    val cursor = contexto.contentResolver.query(uri, null, null, null, null)
    if (cursor != null) {
        if (cursor.moveToFirst()) {
            val indice = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (indice >= 0) nombre = cursor.getString(indice)
        }
        cursor.close()
    }
    return nombre
}