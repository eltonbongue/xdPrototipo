package data.repository

import android.content.Context
import com.example.xdprototipo.data.datasource.DatabaseHelper
import data.DAO.categoriaDAO
import data.DAO.mesaDAO
import data.DAO.pedidoDAO
import data.DAO.produtoDAO
import data.model.Categoria
import data.model.Mesa
import data.model.Pedido
import data.model.Produto

class AppRepository(context: Context) {

    private val produtoDAO = produtoDAO(DatabaseHelper(context))
    private val categoriaDAO = categoriaDAO(DatabaseHelper(context))
    private val mesaDAO = mesaDAO(DatabaseHelper(context))
    private val pedidoDAO = pedidoDAO(DatabaseHelper(context))

    fun inserirProdutos(produto: Produto): Long =
        produtoDAO.insert(produto)

    fun inserirCategoria(categoria: Categoria): Long =
        categoriaDAO.insert(categoria)

    fun inserirMesa(mesa: Mesa): Long =
        mesaDAO.insert(mesa)

    fun inserirPedido(pedido: Pedido): Long =
        pedidoDAO.insert(pedido)






}
