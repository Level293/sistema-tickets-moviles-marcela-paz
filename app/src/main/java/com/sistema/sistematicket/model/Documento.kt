package com.sistema.sistematicket.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "documentos")
data class Documento(

    // Identificador único autoincremental
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val usuarioId: Int,
    val nombre: String,
    val uri: String,
    val tipo: String,
    val mime: String,
    val fecha: Long
)