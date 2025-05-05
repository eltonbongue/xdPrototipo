package data.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import data.model.User
import data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegistroViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserRepository(application)

    private val _mensagem = MutableLiveData<String>()
    val mensagem: LiveData<String> = _mensagem

    fun registrarUsuario(
        nome: String,
        email: String,
        senha: String,
        confirmarSenha: String,
        fotoPath: String
    ) {
        if (nome.isBlank() || email.isBlank() || senha.isBlank() || confirmarSenha.isBlank()) {
            _mensagem.value = "Todos os campos são obrigatórios."
            return
        }

        if (senha != confirmarSenha) {
            _mensagem.value = "As senhas não coincidem."
            return
        }

        val novoUsuario = User(
            id = 0,
            name = nome,
            email = email,
            password = senha,
            photoPath = fotoPath
        )

        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (repository.usuarioExiste(email)) {
                    _mensagem.postValue("Já existe um usuário com esse e-mail.")
                    return@launch
                }

                repository.inserir(novoUsuario)
                _mensagem.postValue("Usuário registrado com sucesso!")
            } catch (e: Exception) {
                _mensagem.postValue("Erro ao registrar usuário: ${e.message}")
            }
        }

    }
}
