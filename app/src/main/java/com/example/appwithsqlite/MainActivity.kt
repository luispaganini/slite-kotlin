package com.example.appwithsqlite

import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appwithsqlite.database.DatabaseHandler
import com.example.appwithsqlite.databinding.ActivityMainBinding
import com.example.appwithsqlite.entity.Cadastro

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButtonListener()

        if (intent.getIntExtra("cod", 0) != 0 ) {
            binding.etCod.setText(intent.getIntExtra("cod", 0).toString())
            binding.etNome.setText(intent.getStringExtra("nome"))
            binding.etTelefone.setText(intent.getStringExtra("telefone"))
        } else {
            binding.btExcluir.visibility = View.GONE
            binding.btPesquisar.visibility = View.GONE
        }

        database = DatabaseHandler(this)
        println("onCreate() executado")
    }

    private fun setButtonListener() {
        binding.btAlterar.setOnClickListener {
            btSalvarOnClick()
        }
        binding.btExcluir.setOnClickListener {
            btExcluirOnClick()
        }
        binding.btPesquisar.setOnClickListener {
            btPesquisarOnClick()
        }
    }


    private fun btSalvarOnClick() {
        if (binding.etCod.text.isEmpty()) {
            database.insertData(
                Cadastro(
                    0,
                    binding.etNome.text.toString(),
                    binding.etTelefone.text.toString()
                )
            )
        } else {
            database.updateData(
                Cadastro(
                    binding.etCod.text.toString().toInt(),
                    binding.etNome.text.toString(),
                    binding.etTelefone.text.toString()
                )
            )
        }

        Toast.makeText(this, "Registro alterado com sucesso", Toast.LENGTH_LONG).show()
        finish()
    }

    private fun btExcluirOnClick() {
        database.deleteData(
            binding.etCod.text.toString()
        )

        Toast.makeText(this, "Registro excluído com sucesso", Toast.LENGTH_LONG).show()
        finish()
    }

    private fun btPesquisarOnClick() {
        val builder = AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Dialog)

        val etCodPesquisar = EditText(this)
        builder.setTitle("Digite o código da pesquisa")
        builder.setView(etCodPesquisar)
        builder.setCancelable(false)
        builder.setNegativeButton("Fechar", null)
        builder.setPositiveButton("Pesquisar") { dialog, id ->
            if (etCodPesquisar.text.isEmpty()) {
                Toast.makeText(this, "Digite o código", Toast.LENGTH_LONG).show()
            } else {
                val registro = database.findById(etCodPesquisar.text.toString().toInt().toString())

                if (registro._id != 0) {
                    binding.etCod.setText(registro._id.toString())
                    binding.etNome.setText(registro.nome)
                    binding.etTelefone.setText(registro.telefone)
                } else
                    Toast.makeText(this, "Registro não encontrado", Toast.LENGTH_LONG).show()
            }
        }
        builder.show()
    }

    companion object {
        private const val COD = 0;
        private const val NOME = 1;
        private const val TELEFONE = 2;
    }

    override fun onStart() {
        super.onStart()
        println("onStart() executado")
    }

    override fun onResume() {
        super.onResume()
        println("onResume() executado")
    }

    override fun onPause() {
        super.onPause()
        println("onPause() executado")
    }

    override fun onStop() {
        super.onStop()
        println("onStop() executado")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("onDestroy() executado")
    }

    override fun onRestart() {
        super.onRestart()
        println("onRestart() executado")
    }
}