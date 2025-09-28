package com.example.appnotas

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.appnotas.data.Nota
import com.example.appnotas.data.NotaViewModel
import com.example.appnotas.databinding.ActivityNotaDetalleBinding
import kotlinx.coroutines.launch
import java.util.*

class NotaDetalleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotaDetalleBinding
    private val notaViewModel: NotaViewModel by viewModels()
    private var notaId: Int = 0
    private var notaActual: Nota? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotaDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarDetalle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbarDetalle.setNavigationOnClickListener {
            finish()
        }

        notaId = intent.getIntExtra("notaId", 0)
        if (notaId != 0) {
            cargarNota(notaId)
        }

        binding.btnGuardar.setOnClickListener {
            guardarCambios()
        }
    }

    private fun cargarNota(id: Int) {
        lifecycleScope.launch {
            notaActual = notaViewModel.obtenerPorId(id)
            notaActual?.let { nota ->
                binding.etTitulo.setText(nota.titulo)
                binding.etCuerpo.setText(nota.cuerpo)
            }
        }
    }

    private fun guardarCambios() {
        val cuerpo = binding.etCuerpo.text.toString()
        if (cuerpo.isBlank()) {
            Toast.makeText(this, "El cuerpo de la nota no puede estar vacío", Toast.LENGTH_SHORT).show()
            return
        }

        val titulo = if (binding.etTitulo.text.toString().isEmpty()) {
            "Nota ${System.currentTimeMillis()}"
        } else {
            binding.etTitulo.text.toString()
        }

        notaActual?.let { nota ->
            val notaActualizada = nota.copy(
                titulo = titulo,
                cuerpo = cuerpo,
                fechaHora = Date()
            )
            notaViewModel.actualizar(notaActualizada)
            finish()
        }
    }
}
