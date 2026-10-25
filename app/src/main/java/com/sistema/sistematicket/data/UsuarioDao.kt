package com.sistema.sistematicket.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sistema.sistematicket.model.Usuario

@Dao
interface UsuarioDAO {

    // agregar un usuario a la tabla usuario
    @Insert
    suspend fun insertar(usuario: Usuario)

    // buscar por correo
    @Query("SELECT * FROM usuarios WHERE correo = :correo LIMIT 1")
    suspend fun buscarPorCorreo(correo: String): Usuario?

    // buscar por id
    @Query("SELECT * FROM usuarios WHERE id = :id LIMIT 1")
    suspend fun buscarPorId(id: Int): Usuario?
}