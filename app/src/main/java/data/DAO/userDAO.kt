package data.DAO

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import data.model.User

class userDAO(private val dbHelper: SQLiteOpenHelper) {

    private val TABLE_NAME = "users"
    private val COLUMN_ID = "id"
    private val COLUMN_NOME = "name"
    private val COLUMN_EMAIL = "email"
    private val COLUMN_PASSWORD = "password"
    private val COLUMN_FOTO = "photo_path"

    fun usuarioExiste(email: String): Boolean {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT 1 FROM $TABLE_NAME WHERE $COLUMN_EMAIL = ?", arrayOf(email))
        val existe = cursor.moveToFirst()
        cursor.close()
        return existe
    }

    fun insert(user: User): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOME, user.name)
            put(COLUMN_EMAIL, user.email)
            put(COLUMN_PASSWORD, user.password)
            put(COLUMN_FOTO, user.photoPath)
        }
        return db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_ABORT)
    }

    fun getAll(): List<User> {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(TABLE_NAME, null, null, null, null, null, null)
        val users = mutableListOf<User>()
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(COLUMN_ID))
                val nome = getString(getColumnIndexOrThrow(COLUMN_NOME))
                val email = getString(getColumnIndexOrThrow(COLUMN_EMAIL))
                val password = getString(getColumnIndexOrThrow(COLUMN_PASSWORD))
                val foto = getString(getColumnIndexOrThrow(COLUMN_FOTO))
                users.add(User(id, nome, email, password, foto))
            }
            close()
        }
        return users
    }

    fun verificarLogin(email: String, password: String): User? {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            null,
            "$COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?",
            arrayOf(email, password),
            null, null, null
        )
        var user: User? = null
        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val nome = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOME))
            val foto = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FOTO))
            user = User(id, nome, email, password, foto)
        }
        cursor.close()
        return user
    }

    fun delete(user: User): Int {
        val db = dbHelper.writableDatabase
        return db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(user.id.toString()))
    }

    fun updateUser(user: User): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOME, user.name)
            put(COLUMN_EMAIL, user.email)
            put(COLUMN_PASSWORD, user.password)
            put(COLUMN_FOTO, user.photoPath)
        }
        return db.update(
            TABLE_NAME,
            values,
            "$COLUMN_ID = ?",
            arrayOf(user.id.toString())
        )
    }
}
