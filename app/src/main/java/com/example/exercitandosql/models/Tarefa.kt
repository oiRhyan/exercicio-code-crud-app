package com.example.exercitandosql.models

import java.io.Serializable

data class Tarefa(
    val id : Int,
    val descricao : String
) : Serializable
