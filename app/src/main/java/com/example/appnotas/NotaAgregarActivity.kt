package com.example.appnotas

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.appnotas.data.Nota
import com.example.appnotas.data.NotaViewModel
import com.example.appnotas.databinding.ActivityNotaAgregarBinding
import java.util.*

class NotaAgregarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotaAgregarBinding
    private val notaViewModel: NotaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotaAgregarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarAgregar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbarAgregar.setNavigationOnClickListener {
            finish()
        }

        binding.btnGuardar.setOnClickListener {
            guardarNota()
        }
    }

    private fun guardarNota() {
        val titulo = if (binding.etTitulo.text.toString().isEmpty()) {
            "Nota ${System.currentTimeMillis()}"
        } else {
            binding.etTitulo.text.toString()
        }

        val cuerpo = binding.etCuerpo.text.toString()
        if (cuerpo.isBlank()) {
            Toast.makeText(this, "El cuerpo de la nota no puede estar vacío", Toast.LENGTH_SHORT).show()
            return
        }

        val nuevaNota = Nota(
            titulo = titulo,
            cuerpo = cuerpo,
            fechaHora = Date()
        )

        notaViewModel.insertar(nuevaNota)
        finish()
    }
}
