package data.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import data.model.Produto
import data.repository.AppRepository

class ProdutoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AppRepository(application)

    private val _mensagem = MutableLiveData<String>()
    val mensagem: LiveData<String> = _mensagem

    fun inserirProduto(
        id: Int,
        nome: String,
        preco: Double,
        categoriaId: Int
    ) {
        if (nome.isBlank() || preco <= 0) {
            _mensagem.value = "Todos os campos são obrigatórios."
            return
        }

        val produto = Produto(id, nome, preco, categoriaId)

        try {
            repository.inserirProdutos(produto)
            _mensagem.value = "Produto adicionado com sucesso!"
        } catch (e: Exception) {
            _mensagem.value = "Erro ao adicionar produto: ${e.message}"
        }


    }

}