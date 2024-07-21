package com.example.exercitandosql.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.exercitandosql.R
import com.example.exercitandosql.models.Tarefa

class TarefaAdapter(
    val DeleteTask : (id : Int?) -> Unit,
    val EditTask : (task : Tarefa?) -> Unit,
) : RecyclerView.Adapter<TarefaAdapter.TarefaViewHolder>() {

    private var listaTarefas: List<Tarefa>? = null

    fun updateList(list: List<Tarefa>) {
        listaTarefas = list
        notifyDataSetChanged()
    }

    inner class TarefaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val descricao: TextView = view.findViewById(R.id.userTask)
        var deletetask : Button = view.findViewById(R.id.removeTask)
        val edittask : Button = view.findViewById(R.id.editTask)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarefaViewHolder {
        val layoutAdapter = LayoutInflater.from(parent.context)
        val inflater = layoutAdapter.inflate(R.layout.recycler_view_item, parent, false)
        return TarefaViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return listaTarefas?.size ?: 0
    }

    override fun onBindViewHolder(holder: TarefaViewHolder, position: Int) {
        if (listaTarefas?.isEmpty() == true) {
            Log.e("ERRO LISTA DE TAREFA", "Lista vazia")
        } else {
            val tarefa = listaTarefas?.get(position)
            holder.descricao.text = tarefa?.descricao
            holder.deletetask.setOnClickListener {
                DeleteTask(tarefa?.id)
            }
            holder.edittask.setOnClickListener {
                EditTask(tarefa)
            }

        }
    }
}
