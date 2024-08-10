package com.example.appwithsqlite

import android.R
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.SimpleCursorAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.appwithsqlite.adapter.MyAdapter
import com.example.appwithsqlite.database.DatabaseHandler
import com.example.appwithsqlite.databinding.ActivityListarBinding

class ListarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListarBinding
    private lateinit var database: DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = DatabaseHandler(this)

        val registros: Cursor = database.readData()

        binding.btIncluir.setOnClickListener {
            btIncluirOnClick()
        }

        val adapter = MyAdapter(this, registros)

        binding.lvPrincipal.adapter = adapter
    }

    private fun btIncluirOnClick() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()

        val registros: Cursor = database.readData()
        val adapter = MyAdapter(this, registros)

        binding.lvPrincipal.adapter = adapter
        println("onStart() executado")
    }
}