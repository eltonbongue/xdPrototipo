package com.example.xdprototipo.data.datasource

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onConfigure(db: SQLiteDatabase) {
        super.onConfigure(db)
        db.execSQL("PRAGMA foreign_keys = ON")
    }


    override fun onCreate(db: SQLiteDatabase) {

        // Usuários
        val createUsersTable = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_EMAIL TEXT NOT NULL,
                $COLUMN_PASSWORD TEXT NOT NULL,
                $COLUMN_PHOTO_PATH TEXT
            );
        """.trimIndent()
        db.execSQL(createUsersTable)

        // Mesas
        val createMesaTable = """
            CREATE TABLE $TABLE_MESA (
                $COLUMN_MESA_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_MESA_NOME TEXT NOT NULL,
                $COLUMN_MESA_ABERTURA INTEGER,
                $COLUMN_MESA_FECHAMENTO INTEGER
            );
        """.trimIndent()
        db.execSQL(createMesaTable)

        // Categorias
        val createCategoriaTable = """
            CREATE TABLE $TABLE_CATEGORIA (
                $COLUMN_CATEGORIA_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_CATEGORIA_NOME TEXT NOT NULL
            );
        """.trimIndent()
        db.execSQL(createCategoriaTable)

        // Produtos
        val createProdutoTable = """
            CREATE TABLE $TABLE_PRODUTO (
                $COLUMN_PRODUTO_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_PRODUTO_NOME TEXT NOT NULL,
                $COLUMN_PRODUTO_PRECO REAL NOT NULL,
                $COLUMN_PRODUTO_CATEGORIA_ID INTEGER,
                FOREIGN KEY ($COLUMN_PRODUTO_CATEGORIA_ID) REFERENCES $TABLE_CATEGORIA($COLUMN_CATEGORIA_ID)
            );
        """.trimIndent()
        db.execSQL(createProdutoTable)

        // Pedidos
        val createPedidoTable = """
            CREATE TABLE $TABLE_PEDIDO (
                $COLUMN_PEDIDO_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_PEDIDO_MESA_ID INTEGER NOT NULL,
                $COLUMN_PEDIDO_PRODUTO_ID INTEGER NOT NULL,
                $COLUMN_PEDIDO_QUANTIDADE INTEGER NOT NULL DEFAULT 1,
                $COLUMN_PEDIDO_OBSERVACOES TEXT,
                $COLUMN_PEDIDO_TIMESTAMP INTEGER DEFAULT (strftime('%s','now')),
                FOREIGN KEY ($COLUMN_PEDIDO_MESA_ID) REFERENCES $TABLE_MESA($COLUMN_MESA_ID),
                FOREIGN KEY ($COLUMN_PEDIDO_PRODUTO_ID) REFERENCES $TABLE_PRODUTO($COLUMN_PRODUTO_ID)
            );
        """.trimIndent()
        db.execSQL(createPedidoTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PEDIDO")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PRODUTO")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CATEGORIA")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_MESA")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    fun listarCategorias(): List<Pair<Int, String>> {
        val categorias = mutableListOf<Pair<Int, String>>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT categoria_id, nome FROM categoria", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("categoria_id"))
                val nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
                categorias.add(id to nome)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return categorias
    }


    companion object {
        const val DATABASE_NAME = "SQLiteDatabase"
        const val DATABASE_VERSION = 1

        // USERS
        const val TABLE_USERS = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_PHOTO_PATH = "photo_path"

        // MESA
        const val TABLE_MESA = "mesa"
        const val COLUMN_MESA_ID = "mesa_id"
        const val COLUMN_MESA_NOME = "nome"
        const val COLUMN_MESA_ABERTURA = "dataHoraAbertura"
        const val COLUMN_MESA_FECHAMENTO = "dataHoraFechamento"

        // CATEGORIA
        const val TABLE_CATEGORIA = "categoria"
        const val COLUMN_CATEGORIA_ID = "categoria_id"
        const val COLUMN_CATEGORIA_NOME = "nome"

        // PRODUTO
        const val TABLE_PRODUTO = "produto"
        const val COLUMN_PRODUTO_ID = "produto_id"
        const val COLUMN_PRODUTO_NOME = "nome"
        const val COLUMN_PRODUTO_PRECO = "preco"
        const val COLUMN_PRODUTO_CATEGORIA_ID = "categoria_id"

        // PEDIDO
        const val TABLE_PEDIDO = "pedido"
        const val COLUMN_PEDIDO_ID = "pedido_id"
        const val COLUMN_PEDIDO_MESA_ID = "mesa_id"
        const val COLUMN_PEDIDO_PRODUTO_ID = "produto_id"
        const val COLUMN_PEDIDO_QUANTIDADE = "quantidade"
        const val COLUMN_PEDIDO_OBSERVACOES = "observacoes"
        const val COLUMN_PEDIDO_TIMESTAMP = "timestamp"
    }
}
