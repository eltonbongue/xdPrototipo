package data.repository

import android.content.Context
import com.example.xdprototipo.data.datasource.DatabaseHelper
import data.DAO.userDAO
import data.model.User

class UserRepository(context: Context) {

    private val userDao = userDAO(DatabaseHelper(context))

    fun inserir(user: User): Long =
        userDao.insert(user)

    fun getTodos(): List<User> =
        userDao.getAll()

    fun verificarLogin(email: String, senha: String): User? =
        userDao.verificarLogin(email, senha)

    fun usuarioExiste(email: String): Boolean =
        userDao.usuarioExiste(email)

    // Chama o 'update' do DAO
    fun updateUser(user: User): Int =
        userDao.updateUser(user)

    // Chama o 'delete' do DAO
    fun deleteUser(user: User): Int =
        userDao.delete(user)
}
