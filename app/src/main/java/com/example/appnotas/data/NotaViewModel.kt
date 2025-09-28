package com.example.appnotas.data

import android.app.Application
import androidx.lifecycle.*
import com.example.appnotas.db.AppDatabase
import kotlinx.coroutines.launch

class NotaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NotaRepository

    val todasLasNotas: LiveData<List<Nota>>

    init {
        val notaDao = AppDatabase.getDatabase(application).notaDao()
        repository = NotaRepository(notaDao)
        todasLasNotas = repository.todasLasNotas
    }

    fun insertar(nota: Nota) = viewModelScope.launch {
        repository.insertar(nota)
    }

    fun actualizar(nota: Nota) = viewModelScope.launch {
        repository.actualizar(nota)
    }

    fun eliminar(nota: Nota) = viewModelScope.launch {
        repository.eliminar(nota)
    }

    suspend fun obtenerPorId(id: Int): Nota? {
        return repository.obtenerPorId(id)
    }
}
