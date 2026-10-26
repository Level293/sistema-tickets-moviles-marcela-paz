package com.sistema.sistematicket.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sistema.sistematicket.model.Documento
import com.sistema.sistematicket.model.Usuario

// defino entidades
@Database(
    entities = [Usuario::class, Documento::class],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {

    // Proporciono acceso a las operaciones de los DAO de usuario y documentos
    abstract fun usuarioDao(): UsuarioDAO
    abstract fun documentoDao(): DocumentoDAO

    companion object {
        private var instancia: AppDatabase? = null

        // Creo la base la primera vez y la reutiliza en todas las pantallas
        fun obtener(contexto: Context): AppDatabase {
            if (instancia == null) {
                instancia = Room.databaseBuilder(
                    contexto.applicationContext,
                    AppDatabase::class.java,
                    "marcela_paz_ticket.db"
                ).fallbackToDestructiveMigration().build()
            }
            return instancia!!
        }
    }
}