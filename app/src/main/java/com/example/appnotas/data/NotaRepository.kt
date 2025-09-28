package com.example.appnotas.data

import androidx.lifecycle.LiveData

class NotaRepository(private val notaDao: NotaDao) {

    val todasLasNotas: LiveData<List<Nota>> = notaDao.obtenerNotas()

    suspend fun insertar(nota: Nota) {
        notaDao.insertarNota(nota)
    }

    suspend fun actualizar(nota: Nota) {
        notaDao.actualizarNota(nota)
    }

    suspend fun eliminar(nota: Nota) {
        notaDao.eliminarNota(nota)
    }

    suspend fun obtenerPorId(id: Int): Nota? {
        return notaDao.obtenerNotaPorId(id)
    }
}
