package com.example.exercitandosql

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exercitandosql.adapter.TarefaAdapter
import com.example.exercitandosql.database.DataBaseHelper
import com.example.exercitandosql.database.TarefaDAO
import com.example.exercitandosql.databinding.ActivityMainBinding
import com.example.exercitandosql.models.Tarefa

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var tarefaAdapter: TarefaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        updateListaTarefa()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val dbHelper = DataBaseHelper(this)
        dbHelper.writableDatabase

        binding.screenTask.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }
    }

    private fun removeTask(id : Int) {
        val taskDelete = TarefaDAO(this)
        taskDelete.delete(id)
        updateListaTarefa()
    }

    private fun setupRecyclerView() {
        tarefaAdapter = TarefaAdapter ({ id -> id?.let { removeTask(it) }}, {task ->  task?.let {editTask(it)}})
        binding.recyclerView.adapter = tarefaAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun editTask(task : Tarefa) {
        val Intent = Intent(this, AddTaskActivity::class.java)
        Intent.putExtra("task", task)
        startActivity(Intent)
    }

    private fun updateListaTarefa() {
        val handlerTarefaDao = TarefaDAO(this)
        val list = handlerTarefaDao.getTask()
        tarefaAdapter.updateList(list)
    }

    override fun onStart() {
        super.onStart()
        updateListaTarefa()
    }
}
