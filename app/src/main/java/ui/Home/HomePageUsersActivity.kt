package ui.Home

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.xdprototipo.databinding.ActivityHomePageUsersBinding
import data.model.User
import data.repository.UserRepository
import java.io.File

class HomePageUsersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomePageUsersBinding
    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(binding.root.id)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        userRepository = UserRepository(this)
        loadUserFromSession()
    }

    private fun loadUserFromSession() {
        val sharedPref = getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val email = sharedPref.getString("email", null)
        val senha = sharedPref.getString("senha", null)

        if (email != null && senha != null) {
            val user = userRepository.verificarLogin(email, senha)
            showUserInfo(user)
        }
    }

    private fun showUserInfo(user: User?) {
        user?.let {
            binding.textViewUserName.text = it.name

            it.photoPath?.let { path ->
                val file = File(path)
                if (file.exists()) {
                    val bitmap = BitmapFactory.decodeFile(path)
                    binding.imageViewUserHome.setImageBitmap(bitmap)
                }
            }
        }
    }
}
