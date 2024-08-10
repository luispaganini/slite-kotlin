package com.example.appwithsqlite.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.appwithsqlite.entity.Cadastro

class DatabaseHandler (context: Context) : SQLiteOpenHelper (context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_NOME TEXT, $COLUMN_TELEFONE TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertData(cadastro: Cadastro) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_NOME, cadastro.nome)
        contentValues.put(COLUMN_TELEFONE, cadastro.telefone)
        db.insert(TABLE_NAME, null, contentValues)
    }

    fun readData(): Cursor{
        val db = this.readableDatabase
        val registro = db.query(
            TABLE_NAME,
            null,
            null, null, null, null, null)

        return registro
    }

    fun findById(id: String): Cadastro {
        val db = this.readableDatabase
        val values = db.query(
            TABLE_NAME,
            arrayOf(COLUMN_ID, COLUMN_NOME, COLUMN_TELEFONE),
            "$COLUMN_ID = ?",
            arrayOf(id),
            null,
            null,
            null
        )
        values.moveToFirst()
        return Cadastro(values.getInt(0), values.getString(1), values.getString(2))
    }

    fun updateData(cadastro: Cadastro) {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(COLUMN_NOME, cadastro.nome)
        contentValues.put(COLUMN_TELEFONE, cadastro.telefone)
        db.update(TABLE_NAME, contentValues, "$COLUMN_ID = ?", arrayOf(cadastro._id.toString()))

    }

    fun deleteData(id: String): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id))
    }

    companion object {
        private const val DATABASE_NAME = "dbfile.sqlite"
        private const val DATABASE_VERSION = 3
        private const val TABLE_NAME = "cadastro"
        private const val COLUMN_ID = "_id"
        const val COLUMN_NOME = "nome"
        const val COLUMN_TELEFONE = "telefone"
        const val COD = 0
        const val NOME = 1
        const val TELEFONE = 2

    }
}