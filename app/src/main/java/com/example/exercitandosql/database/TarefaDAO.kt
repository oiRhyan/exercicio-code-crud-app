package com.example.exercitandosql.database

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.exercitandosql.models.Tarefa

class TarefaDAO(context: Context) : iTarefaDAO {

    private val dbHelper = DataBaseHelper(context)
    private val db = dbHelper.writableDatabase

    override fun salvar(item: Tarefa): Boolean {
        val values = ContentValues().apply {
            put(DataBaseHelper.DATABASE_DESC, item.descricao)
        }

        return try {
            db.insert(DataBaseHelper.DATABASE_TABLE, null, values)
            Log.v("Insert Command", "Tarefa cadastrada com sucesso!")
            true
        } catch (e: Exception) {
            Log.e("Insert Command", "Erro ao inserir tarefa: ${e.message}")
            false
        }
    }

    override fun delete(id: Int): Boolean {
        try {
            dbHelper.writableDatabase.delete(
                DataBaseHelper.DATABASE_TABLE, "${DataBaseHelper.DATABASE_ID} = ${id}", null
            )
        } catch (e: Exception) {
            Log.e("Delete Task", "Erro ao deletar tarefa: ${e.message}")
            return false
        }
        return true
    }

    override fun edit(item: Tarefa): Boolean {
        val listIDs = arrayOf(item.id.toString())
        val newDesc = item.descricao
        val values = ContentValues().apply {
            put("descricao", newDesc)
        }

        return try {
            val rowsAffected = dbHelper.writableDatabase.update(
                DataBaseHelper.DATABASE_TABLE,
                values,
                "${DataBaseHelper.DATABASE_ID} = ?",
                listIDs
            )
            Log.v("Update Task", "Atualizado com sucesso! Linhas afetadas: $rowsAffected")
            rowsAffected > 0
        } catch (e: Exception) {
            Log.e("Update Task", "Erro ao atualizar tarefa: ${e.message}")
            false
        }
    }


    override fun getTask(): List<Tarefa> {
        val listaTarefas = mutableListOf<Tarefa>()
        val querySelector = "SELECT * FROM ${DataBaseHelper.DATABASE_TABLE};"
        val cursor = db.rawQuery(querySelector, null)

        val indexID = cursor.getColumnIndexOrThrow(DataBaseHelper.DATABASE_ID)
        val indexDesc = cursor.getColumnIndexOrThrow(DataBaseHelper.DATABASE_DESC)

        try {
            while (cursor.moveToNext()) {
                val id = cursor.getInt(indexID)
                val desc = cursor.getString(indexDesc)

                Log.v("ListTarefa", "ID: $id" +
                        "Desc: $desc")
                listaTarefas.add(Tarefa(id, desc))
            }
            Log.v("DataBase Request", "Sucesso ao retornar lista de tarefas")
        } catch (e : Exception) {
            Log.e("DataBase Request", "Erro ao retornar lista de tarefa: ${e.message}")
        }

        return listaTarefas
    }
}
