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

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserRepository(application)

    private val _usuarios = MutableLiveData<List<User>>()
    val usuarios: LiveData<List<User>> get() = _usuarios

    fun carregarUsuarios() {
        viewModelScope.launch(Dispatchers.IO) {
            val users = repository.getTodos()
            _usuarios.postValue(users)
        }
    }
}
