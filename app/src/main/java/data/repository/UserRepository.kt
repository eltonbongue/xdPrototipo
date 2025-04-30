package data.repository

import android.content.Context
import com.example.xdprototipo.data.datasource.DatabaseHelper
import data.DAO.userDAO
import data.model.User


class UserRepository(context: Context) {

    private val userDao = userDAO(DatabaseHelper(context))

    fun inserir(user: User): Long {
        return userDao.insert(user)
    }

    fun getTodos(): List<User> {
        return userDao.getAll()
    }

    fun verificarLogin(email: String, senha: String): User? {
        return userDao.verificarLogin(email, senha)
    }

    fun deletar(user: User): Int {
        return userDao.delete(user)
    }
}
