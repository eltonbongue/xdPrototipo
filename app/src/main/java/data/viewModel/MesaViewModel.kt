package data.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import data.model.Mesa
import data.repository.AppRepository
import kotlinx.coroutines.launch

class MesaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AppRepository(application)

    private val _mensagem = MutableLiveData<String>()
    val mensagem: LiveData<String> = _mensagem

    fun inserirMesa(
        nome: String,
        dataHoraAbertura: Int,
        dataHoraFechamento: Int
    ) {
        if (nome.isBlank()) {
            _mensagem.value = "Todos os campos são obrigatórios."
            return
        }

        val novaMesa = Mesa(
            mesa_id = 0, // será auto-incrementado no banco
            nome = nome,
            dataHoraAbertura = dataHoraAbertura,
            dataHoraFechamento = dataHoraFechamento
        )

        viewModelScope.launch {
            try {
                repository.inserirMesa(novaMesa)
                _mensagem.value = "Mesa cadastrada com sucesso!"
            } catch (e: Exception) {
                _mensagem.value = "Erro ao cadastrar mesa: ${e.message}"
            }
        }
    }
}
