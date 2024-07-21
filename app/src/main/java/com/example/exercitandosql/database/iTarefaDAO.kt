package com.example.exercitandosql.database

import com.example.exercitandosql.models.Tarefa

interface iTarefaDAO {
    fun salvar(item : Tarefa) : Boolean
    fun delete(id : Int) : Boolean
    fun edit(item : Tarefa) : Boolean
    fun getTask() : List<Tarefa>
}