package com.example.appwithsqlite.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import com.example.appwithsqlite.MainActivity
import com.example.appwithsqlite.R
import com.example.appwithsqlite.database.DatabaseHandler.Companion.COD
import com.example.appwithsqlite.database.DatabaseHandler.Companion.NOME
import com.example.appwithsqlite.database.DatabaseHandler.Companion.TELEFONE
import com.example.appwithsqlite.entity.Cadastro

class MyAdapter (val context : Context, val dataSource : Cursor): BaseAdapter() {
    override fun getCount(): Int {
        return dataSource.count
    }

    override fun getItem(position: Int): Any {
        dataSource.moveToPosition(position)
        val cadastro = Cadastro(
            dataSource.getInt(COD),
            dataSource.getString(NOME),
            dataSource.getString(TELEFONE)
        )
        return cadastro
    }

    override fun getItemId(position: Int): Long {
        dataSource.moveToPosition(position)
        return dataSource.getInt(COD).toLong()
    }

    @SuppressLint("MissingInflatedId")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inflater.inflate(R.layout.elemento_lista, null)

        val tvNomeElementoLista = v.findViewById<TextView>(R.id.tvNomeElementoLista)
        val tvTelefoneElementoLista = v.findViewById<TextView>(R.id.tvTelefoneElementoLista)
        val btEditarElementoLista = v.findViewById<ImageButton>(R.id.btEditarElementoLista)

        dataSource.moveToPosition(position)
        tvNomeElementoLista.setText(dataSource.getString(1))
        tvTelefoneElementoLista.setText(dataSource.getString(2))

        btEditarElementoLista.setOnClickListener {
            dataSource.moveToPosition(position)

            val intent = Intent(context, MainActivity::class.java)

            intent.putExtra("cod", dataSource.getInt(COD))
            intent.putExtra("nome", dataSource.getString(NOME))
            intent.putExtra("telefone", dataSource.getString(TELEFONE))

            context.startActivity(intent)
        }

        return v

    }

}