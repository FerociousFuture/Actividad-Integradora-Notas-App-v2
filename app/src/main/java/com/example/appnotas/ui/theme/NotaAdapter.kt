package com.example.appnotas.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appnotas.R
import com.example.appnotas.data.Nota
import java.text.SimpleDateFormat
import java.util.*

class NotaAdapter(
    private val notas: List<Nota>,
    private val onClick: (Nota) -> Unit,
    private val onLongClick: (Nota) -> Unit
) : RecyclerView.Adapter<NotaAdapter.NotaViewHolder>() {

    inner class NotaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titulo: TextView = view.findViewById(R.id.tvTitulo)
        val fecha: TextView = view.findViewById(R.id.tvFecha)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_nota, parent, false)
        return NotaViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotaViewHolder, position: Int) {
        val nota = notas[position]
        holder.titulo.text = nota.titulo
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        holder.fecha.text = sdf.format(nota.fechaHora)

        holder.itemView.setOnClickListener { onClick(nota) }
        holder.itemView.setOnLongClickListener { onLongClick(nota); true }
    }

    override fun getItemCount(): Int = notas.size
}
