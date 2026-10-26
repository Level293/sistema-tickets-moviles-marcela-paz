package com.sistema.sistematicket.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.sistema.sistematicket.model.Documento

@Dao
interface DocumentoDAO {

    // Inserta un documento en la tabla
    @Insert
    suspend fun insertar(documento: Documento)

    // Recupera los documentos de un usuario, el más nuevo primero
    @Query("SELECT * FROM documentos WHERE usuarioId = :usuarioId ORDER BY fecha DESC")
    suspend fun listar(usuarioId: Int): List<Documento>

    // Elimina un documento
    @Delete
    suspend fun eliminar(documento: Documento)
}