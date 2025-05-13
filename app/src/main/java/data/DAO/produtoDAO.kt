package data.DAO

import android.content.ContentValues
import android.database.sqlite.SQLiteOpenHelper
import data.model.Produto

class produtoDAO (private val dbHelper: SQLiteOpenHelper) {

    private val table = "produto"
    private val columnId = "produto_id"
    private val columnNome = "nome"
    private val columnPreco = "preco"
    private val columnCategoriaId = "categoria_id"


    fun insert (produto: Produto): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(columnNome, produto.nome)
            put(columnPreco, produto.preco)
            put(columnCategoriaId, produto.categoriaId)
        }
        return db.insert(table, null, values)
    }

    fun update (produto: Produto): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(columnNome, produto.nome)
            put(columnPreco, produto.preco)
            put(columnCategoriaId, produto.categoriaId)
        }
        return db.update(table, values, "$columnId = ?", arrayOf(produto.id.toString()))
    }

    fun delete (id: Int): Int {
        val db = dbHelper.writableDatabase
        return db.delete(table, "$columnId = ?", arrayOf(id.toString()))
    }

}