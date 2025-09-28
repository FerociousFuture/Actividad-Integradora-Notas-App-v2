package com.example.appnotas

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appnotas.data.Nota
import com.example.appnotas.data.NotaViewModel
import com.example.appnotas.databinding.ActivityMainBinding
import com.example.appnotas.ui.NotaAdapter
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val notaViewModel: NotaViewModel by viewModels()
    private var notasList: List<Nota> = emptyList()
    private var notaSeleccionada: Nota? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarMain)
        setupRecyclerView()
        observeNotas()
        setupToolbarActions()
    }

    private fun setupRecyclerView() {
        binding.recyclerNotas.layoutManager = LinearLayoutManager(this)
    }

    private fun observeNotas() {
        notaViewModel.todasLasNotas.observe(this) { notas ->
            notasList = notas
            binding.recyclerNotas.adapter = NotaAdapter(notasList,
                onClick = { nota ->
                    notaSeleccionada = nota
                    // doble clic: abrimos detalle/editar
                    val intent = Intent(this, NotaDetalleActivity::class.java)
                    intent.putExtra("notaId", nota.id)
                    startActivity(intent)
                },
                onLongClick = { nota ->
                    notaSeleccionada = nota
                    Snackbar.make(
                        binding.root,
                        "Nota seleccionada: ${nota.titulo}",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            )
        }
    }

    private fun setupToolbarActions() {
        binding.toolbarMain.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_agregar -> {
                    val intent = Intent(this, NotaAgregarActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.action_eliminar -> {
                    if (notaSeleccionada == null) {
                        Snackbar.make(binding.root, "Seleccione una nota primero", Snackbar.LENGTH_SHORT).show()
                    } else {
                        Snackbar.make(binding.root, "¿Eliminar nota?", Snackbar.LENGTH_LONG)
                            .setAction("Sí") {
                                notaSeleccionada?.let {
                                    notaViewModel.eliminar(it)
                                    notaSeleccionada = null
                                }
                            }.show()
                    }
                    true
                }
                else -> false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresca la lista al volver de agregar/editar
        binding.recyclerNotas.adapter?.notifyDataSetChanged()
    }
}
