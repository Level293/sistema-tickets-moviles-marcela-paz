package com.sistema.sistematicket.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sistema.sistematicket.model.Usuario

// defino entidades
@Database(
    entities = [Usuario::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    // Proporciono acceso a las operaciones del DAO.
    abstract fun usuarioDao(): UsuarioDAO

    companion object {
        private var instancia: AppDatabase? = null

        // Crea la base la primera vez y la reutiliza en todas las pantallas.
        fun obtener(contexto: Context): AppDatabase {
            if (instancia == null) {
                instancia = Room.databaseBuilder(
                    contexto.applicationContext,
                    AppDatabase::class.java,
                    "marcela_paz_ticket.db"
                ).build()
            }
            return instancia!!
        }
    }
}