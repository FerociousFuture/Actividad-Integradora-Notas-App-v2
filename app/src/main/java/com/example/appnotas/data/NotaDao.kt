package com.example.appnotas.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NotaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarNota(nota: Nota)

    @Update
    suspend fun actualizarNota(nota: Nota)

    @Delete
    suspend fun eliminarNota(nota: Nota)

    @Query("SELECT * FROM notas ORDER BY fechaHora DESC")
    fun obtenerNotas(): LiveData<List<Nota>>

    @Query("SELECT * FROM notas WHERE id = :id LIMIT 1")
    suspend fun obtenerNotaPorId(id: Int): Nota?
}
