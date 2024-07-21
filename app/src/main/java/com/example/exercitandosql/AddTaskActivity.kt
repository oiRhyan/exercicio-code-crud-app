    package com.example.exercitandosql

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.exercitandosql.adapter.TarefaAdapter
import com.example.exercitandosql.database.TarefaDAO
import com.example.exercitandosql.databinding.ActivityAddTaskBinding
import com.example.exercitandosql.models.Tarefa

    class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddTaskBinding
    private var tarefa : Tarefa? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.addTask.setOnClickListener {
            handlerTask()
        }

        val bundle = intent.extras
        if(bundle != null && bundle.containsKey("task")) {
            tarefa = bundle.getSerializable("task") as Tarefa
            binding.taskInput.setText(tarefa?.descricao)
        }

    }

        private fun handlerTask() {
            if(tarefa != null) {
                edit_task()
            } else {
                save_task()
            }
        }


        private fun edit_task() {
            val descricao = binding.taskInput.text.toString()
            val TarefaDAo = TarefaDAO(this)
            val Tarefa = Tarefa(tarefa!!.id, descricao)
            TarefaDAo.edit(Tarefa)
            Toast.makeText(this, "Tarefa atualizada com sucesso!", Toast.LENGTH_LONG).show()
            finish()
        }


        private fun save_task() {
            val descricao = binding.taskInput.text.toString()
            val TarefaDAO = TarefaDAO(this)
            val Tarefa : Tarefa = Tarefa(tarefa!!.id, descricao)

            TarefaDAO.salvar(Tarefa)
            Toast.makeText(this, "Tarefa cadastrada com sucesso!", Toast.LENGTH_LONG).show()
            finish()
        }

}
