package ui.Home

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.xdprototipo.R
import com.example.xdprototipo.databinding.ActivityAlterarUsersBinding
import data.model.User
import data.viewModel.UserViewModel

class AlterarUsersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlterarUsersBinding
    private lateinit var userViewModel: UserViewModel
    private var userPhotoPath: String = ""

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            try {
                contentResolver.takePersistableUriPermission(it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                val inputStream = contentResolver.openInputStream(it)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                inputStream?.close()

                if (bitmap != null) {
                    val userId = intent.getIntExtra("user_id", -1)
                    val novoCaminho = userViewModel.saveUserImageToInternalStorage(applicationContext, bitmap, userId)
                    userPhotoPath = novoCaminho
                    binding.imageViewUser.setImageBitmap(bitmap)
                } else {
                    showToast("Erro ao carregar imagem")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                showToast("Erro ao acessar imagem")
            }
        } ?: showToast("Nenhuma imagem selecionada")
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

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

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
            userViewModel.actualizarUsuario(updatedUser)
            showToast("Usuário atualizado com sucesso")
            finish()
        }

        binding.btnApagar.setOnClickListener {
            val userToDelete = User(id = userId, name = "", email = "", password = "", photoPath = "")
            userViewModel.deletarUsuario(userToDelete)
            showToast("Usuário deletado com sucesso")
            finish()
        }

        binding.btnChoosePhoto.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
