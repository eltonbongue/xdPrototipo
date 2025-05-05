package ui.Home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.xdprototipo.R
import com.example.xdprototipo.databinding.ActivityAlterarUsersBinding
import data.model.User
import data.repository.UserRepository

class AlterarUsersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlterarUsersBinding
    private lateinit var userRepository: UserRepository
    private var userPhotoPath: String = ""


    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {

            try {
                contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (e: SecurityException) {

            }

            userPhotoPath = uri.toString()
            binding.imageViewUser.setImageURI(uri)
        } else {
            Toast.makeText(this, "Nenhuma imagem selecionada", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlterarUsersBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val sys = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(sys.left, sys.top, sys.right, sys.bottom)
            insets
        }

        userRepository = UserRepository(this)


        val userId = intent.getIntExtra("user_id", -1)
        val userName = intent.getStringExtra("user_name") ?: ""
        val userEmail = intent.getStringExtra("user_email") ?: ""
        val userPass = intent.getStringExtra("user_password") ?: ""
        val userPhoto = intent.getStringExtra("user_photo") ?: ""
        userPhotoPath = userPhoto


        binding.editName.setText(userName)
        binding.editEmail.setText(userEmail)
        binding.editPassword.setText(userPass)
        binding.editConfirmPassword.setText("")


        if (userPhotoPath.isNotEmpty()) {
            try {
                binding.imageViewUser.setImageURI(Uri.parse(userPhotoPath))
            } catch (e: Exception) {
                binding.imageViewUser.setImageResource(R.drawable.ic_user_placeholder)
            }
        } else {
            binding.imageViewUser.setImageResource(R.drawable.ic_user_placeholder)
        }


        binding.btnActualizar.setOnClickListener {
            val updatedUser = User(
                id = userId,
                name = binding.editName.text.toString(),
                email = binding.editEmail.text.toString(),
                password = binding.editConfirmPassword.text.toString().ifBlank { userPass },
                photoPath = userPhotoPath
            )
            val rows = userRepository.updateUser(updatedUser)
            Toast.makeText(this, "Linhas atualizadas: $rows", Toast.LENGTH_SHORT).show()
            finish()
        }


        binding.btnApagar.setOnClickListener {
            val userToDelete = User(
                id = userId,
                name = "",
                email = "",
                password = "",
                photoPath = ""
            )
            val rows = userRepository.deleteUser(userToDelete)
            Toast.makeText(this, "Linhas deletadas: $rows", Toast.LENGTH_SHORT).show()
            finish()
        }


        binding.btnChoosePhoto.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }
    }
}
