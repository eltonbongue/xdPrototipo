package ui.Home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.xdprototipo.R
import com.example.xdprototipo.databinding.ActivityDefinicoesBinding
import data.model.User

class definicoesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDefinicoesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDefinicoesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.textViewBack.setOnClickListener {
            finish()
        }

        binding.textViewUsers.setOnClickListener {
            // Substitua pelos dados reais do usuário, se tiver autenticação implementada
            val user = User(
                id = 1,
                name = "admin",
                email = "eltonbonguexd@example.com",
                password = "1727" // Senha de verificação
            )
            showPasswordDialog(user)
        }
    }

    private fun showPasswordDialog(user: User) {
        val input = EditText(this).apply {
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            hint = "Digite a senha"
        }

        AlertDialog.Builder(this)
            .setTitle("Autenticação")
            .setMessage("Insira a senha para acessar o perfil de ${user.name}")
            .setView(input)
            .setPositiveButton("OK") { dialog, _ ->
                val enteredPassword = input.text.toString()
                if (enteredPassword == user.password) {
                    Toast.makeText(this, "Acesso concedido", Toast.LENGTH_SHORT).show()

                    val sharedPref = getSharedPreferences("user_session", Context.MODE_PRIVATE)
                    with(sharedPref.edit()) {
                        putString("email", user.email)
                        putString("senha", user.password)
                        apply()
                    }

                    startActivity(Intent(this, DefinicoesUsersActivity::class.java))
                } else {
                    Toast.makeText(this, "Senha incorreta", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}
