package data.repository

import android.content.Context
import android.graphics.Bitmap
import com.example.xdprototipo.data.datasource.DatabaseHelper
import data.DAO.userDAO
import data.model.User
import java.io.File
import java.io.FileOutputStream

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


    fun updateUser(user: User): Int =
        userDao.updateUser(user)

    fun deleteUser(user: User): Int =
        userDao.delete(user)

    fun saveUserImageToInternalStorage(context: Context, bitmap: Bitmap, userId: Int): String {
        val directory = File(context.filesDir, "profile_images")
        if (!directory.exists()) {
            directory.mkdirs()
        }

        val fileName = "user_$userId.png"
        val file = File(directory, fileName)

        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        }

        return file.absolutePath
    }


}
