import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import data.model.Categoria
import data.repository.AppRepository
import kotlinx.coroutines.launch

class CategoriaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AppRepository(application)

    private val _mensagem = MutableLiveData<String>()
    val mensagem: LiveData<String> = _mensagem

    fun inserirCategoria(nome: String) {
        if (nome.isBlank()) {
            _mensagem.value = "Todos os campos são obrigatórios."
            return
        }

        val novaCategoria = Categoria(0, nome = nome)

        viewModelScope.launch {
            try {
                repository.inserirCategoria(novaCategoria)
                _mensagem.value = "Categoria cadastrada com sucesso!"
            } catch (e: Exception) {
                _mensagem.value = "Erro ao cadastrar categoria: ${e.message}"
            }
        }
    }
}
