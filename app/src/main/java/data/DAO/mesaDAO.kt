package data.DAO

import android.content.ContentValues
import android.database.sqlite.SQLiteOpenHelper
import data.model.Mesa

class mesaDAO  (private val dbHelper: SQLiteOpenHelper) {

    private val table = "mesa"
    private val columnId = "mesa_id"
    private val columnNome = "nome"
    private val columnAbertura = "abertura"
    private val columnFechamento = "fechamento"


    fun insert (mesa: Mesa): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(columnNome, mesa.nome)
            put(columnAbertura, mesa.dataHoraAbertura)
            put(columnFechamento, mesa.dataHoraFechamento)
        }
        return db.insert(table, null, values)
    }

    fun update (mesa: Mesa): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(columnNome, mesa.nome)
            put(columnAbertura, mesa.dataHoraAbertura)
            put(columnFechamento, mesa.dataHoraFechamento)
        }
        return db.update(table, values, "$columnId = ?", arrayOf(mesa.mesa_id.toString()))
    }

    fun delete (id: Int): Int {
        val db = dbHelper.writableDatabase
        return db.delete(table, "$columnId = ?", arrayOf(id.toString()))
    }
}