package data.DAO

import android.content.ContentValues
import android.database.sqlite.SQLiteOpenHelper
import data.model.Pedido

class pedidoDAO  (private val dbHelper: SQLiteOpenHelper) {

    private val table = "pedido"
    private val columnId = "pedido_id"
    private val columnMesaId = "mesa_id"
    private val columnProdutoId = "produto_id"
    private val columnQuantidade = "quantidade"
    private val columnObservacoes = "observacoes"
    private val columnTimestamp = "timestamp"


    fun insert (pedido: Pedido): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(columnMesaId, pedido.mesa_id)
            put(columnProdutoId, pedido.produto_id)
            put(columnQuantidade, pedido.quantidade)
            put(columnObservacoes, pedido.observacoes)
            put(columnTimestamp, pedido.timestamp)
        }
        return db.insert(table, null, values)
    }


    fun update (pedido: Pedido): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(columnMesaId, pedido.mesa_id)
            put(columnProdutoId, pedido.produto_id)
            put(columnQuantidade, pedido.quantidade)
            put(columnObservacoes, pedido.observacoes)
            put(columnTimestamp, pedido.timestamp)
        }
        return db.update(table, values, "$columnId = ?", arrayOf(pedido.pedido_id.toString()))
    }

    fun delete (id: Int): Int {
        val db = dbHelper.writableDatabase
        return db.delete(table, "$columnId = ?", arrayOf(id.toString()))
    }

}