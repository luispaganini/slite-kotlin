package com.example.appwithsqlite

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appwithsqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButtonListener()

        database = SQLiteDatabase.openOrCreateDatabase(
            this.getDatabasePath("dbfile.sqlite"),
            null
        )

        database.execSQL("CREATE TABLE IF NOT EXISTS cadastro" +
                "(_id INTEGER PRIMARY KEY, nome TEXT, telefone TEXT)")

    }

    private fun setButtonListener() {
        binding.btIncluir.setOnClickListener {
            btIncluirOnClick()
        }
        binding.btAlterar.setOnClickListener {
            btAlterarOnClick()
        }
        binding.btExcluir.setOnClickListener {
            btExcluirOnClick()
        }
        binding.btPesquisar.setOnClickListener {
            btPesquisarOnClick()
        }
        binding.btListar.setOnClickListener {
            btListarOnClick()
        }
    }

    private fun btIncluirOnClick() {
        var registro = ContentValues()

        registro.put("nome", binding.etNome.text.toString())
        registro.put("telefone", binding.etTelefone.text.toString())

        database.insert("cadastro", null, registro)

        Toast.makeText(this, "Registro inserido com sucesso", Toast.LENGTH_LONG).show()
    }

    private fun btAlterarOnClick() {
        val registro = ContentValues()
        registro.put("nome", binding.etNome.text.toString())
        registro.put("telefone", binding.etTelefone.text.toString())
        database.update(
            "cadastro",
            registro,
            "_id = ?",
            arrayOf(binding.etCod.text.toString()))


        Toast.makeText(this, "Registro alterado com sucesso", Toast.LENGTH_LONG).show()
    }

    private fun btExcluirOnClick() {
        database.delete("cadastro", "_id = ?", arrayOf(binding.etCod.text.toString()))

        Toast.makeText(this, "Registro excluído com sucesso", Toast.LENGTH_LONG).show()
    }

    private fun btPesquisarOnClick() {
        val registro = database.query(
            "cadastro",
            null,
            "_id = ${binding.etCod.text}",
            null,
            null,
            null,
            null
        )

        if (registro.moveToNext()) {
            binding.etNome.setText(registro.getString(NOME).toString())
            binding.etTelefone.setText(registro.getInt(TELEFONE).toString())
        } else {
            Toast.makeText(this, "Registro não encontrado", Toast.LENGTH_LONG).show()
        }
    }

    private fun btListarOnClick() {
        val registro = database.query(
            "cadastro",
            null,
            null,
            null,
            null,
            null,
            null
        )
        val saida = StringBuilder()
        while (registro.moveToNext()) {
            saida.append((registro.getInt(COD)))
            saida.append(" - ")
            saida.append(registro.getString(1))
            saida.append(" - ")
            saida.append(registro.getInt(TELEFONE))
            saida.append("\n")

        }

        Toast.makeText(this, saida.toString(), Toast.LENGTH_LONG).show()
    }
    companion object {
        private const val COD = 0;
        private const val NOME = 1;
        private const val TELEFONE = 2;
    }
}