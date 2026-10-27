package com.sistema.sistematicket.utils

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.core.content.FileProvider
import java.io.File

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

// crea un archivo vacío donde la cámara guardará la foto
fun crearArchivoFoto(contexto: Context): File {
    val carpeta = File(contexto.filesDir, "fotos")
    carpeta.mkdirs()
    return File(carpeta, "foto_${System.currentTimeMillis()}.jpg")
}

// convierte el archivo en una dirección segura que se puede compartir
fun uriDeArchivo(contexto: Context, archivo: File): Uri {
    return FileProvider.getUriForFile(
        contexto,
        "${contexto.packageName}.fileprovider",
        archivo
    )
}

// Borra la foto del teléfono al eliminarla
fun eliminarFoto(contexto: Context, nombre: String) {
    File(File(contexto.filesDir, "fotos"), nombre).delete()
}