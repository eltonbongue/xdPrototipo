package ui.Home

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.xdprototipo.databinding.ActivityRegistrarUserBinding
import data.viewModel.RegistroViewModel
import data.viewModel.RegistroViewModelFactory
import java.io.File
import java.io.FileOutputStream

class registrarUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrarUserBinding
    private lateinit var viewModel: RegistroViewModel
    private val SELECIONAR_IMAGEM = 1001
    private var selectedBitmap: Bitmap? = null
    private var savedImagePath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        viewModel = ViewModelProvider(this, RegistroViewModelFactory(application))[RegistroViewModel::class.java]

        binding.btnChoosePhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, SELECIONAR_IMAGEM)
        }

        binding.btnRegister.setOnClickListener {
            registrarUser()
        }

        viewModel.mensagem.observe(this) { msg ->
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            if (msg.contains("sucesso", ignoreCase = true)) {
                limparCampos()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(binding.root.id)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun registrarUser() {
        val nome = binding.editName.text.toString().trim()
        val email = binding.editEmail.text.toString().trim()
        val senha = binding.editPassword.text.toString().trim()
        val confirmarSenha = binding.editConfirmPassword.text.toString().trim()
        val fotoPath = savedImagePath ?: ""

        if (nome.isBlank() || email.isBlank() || senha.isBlank() || confirmarSenha.isBlank() || fotoPath.isBlank()) {
            Toast.makeText(this, "Todos os campos são obrigatórios.", Toast.LENGTH_SHORT).show()
            return
        }

        if (senha != confirmarSenha) {
            Toast.makeText(this, "As senhas não coincidem.", Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.registrarUsuario(nome, email, senha, confirmarSenha, fotoPath)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SELECIONAR_IMAGEM && resultCode == Activity.RESULT_OK && data != null) {
            val uri = data.data
            if (uri != null) {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                selectedBitmap = bitmap
                binding.imageViewUser.setImageBitmap(bitmap)

                // Salvar diretamente como arquivo
                savedImagePath = salvarBitmapInternamente(bitmap)
                if (savedImagePath == null) {
                    Toast.makeText(this, "Erro ao salvar a imagem.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun salvarBitmapInternamente(bitmap: Bitmap): String? {
        return try {
            val file = File(filesDir, "foto_${System.currentTimeMillis()}.jpg")
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
            file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun limparCampos() {
        binding.editName.text.clear()
        binding.editEmail.text.clear()
        binding.editPassword.text.clear()
        binding.editConfirmPassword.text.clear()
        binding.imageViewUser.setImageResource(0)
        selectedBitmap = null
        savedImagePath = null
    }
}
