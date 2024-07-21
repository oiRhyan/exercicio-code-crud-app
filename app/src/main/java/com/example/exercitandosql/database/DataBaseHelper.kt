package com.example.exercitandosql.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DataBaseHelper(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null, 1, null
) {

    companion object {
        const val DATABASE_NAME = "tasks.db"
        const val DATABASE_ID = "id"
        const val DATABASE_DESC = "descricao"
        const val DATABASE_TABLE = "tarefas"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE $DATABASE_TABLE (" +
                "   $DATABASE_ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "   $DATABASE_DESC VARCHAR(255)" +
                ");"

        try {
            db?.execSQL(query)
            Log.v("SQL Status", "Tabela criada com sucesso!")
        } catch (e: Exception) {
            Log.e("SQL Status", "Erro ao criar tabela: ${e.message}")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}
