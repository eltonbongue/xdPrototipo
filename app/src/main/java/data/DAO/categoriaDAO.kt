package data.DAO

import android.content.ContentValues
import android.database.sqlite.SQLiteOpenHelper
import data.model.Categoria

class categoriaDAO(private val dbHelper: SQLiteOpenHelper) {

    private val table = "categoria"
    private val columnId = "categoria_id"
    private val columnNome = "nome"

    fun insert(categoria: Categoria): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(columnNome, categoria.nome)
        }
        return db.insert(table, null, values)
    }
    fun update(categoria: Categoria): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(columnNome, categoria.nome)
        }
        return db.update(table, values, "$columnId = ?", arrayOf(categoria.categoria_id.toString()))
    }

    fun delete(id: Int): Int {
        val db = dbHelper.writableDatabase
        return db.delete(table, "$columnId = ?", arrayOf(id.toString()))
    }



}