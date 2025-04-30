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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xdprototipo.R
import com.example.xdprototipo.databinding.ActivityUtilizadoresBinding
import data.model.User
import data.viewModel.UserViewModel
import ui.adapters.UserAdapter

class utilizadoresActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUtilizadoresBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUtilizadoresBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.textViewBack.setOnClickListener { finish() }
        binding.button.setOnClickListener { navigateToRegistrar() }

        adapter = UserAdapter(emptyList()) { user ->
            showPasswordDialog(user)
        }

        binding.RecyclerViewUsers.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.RecyclerViewUsers.adapter = adapter

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        userViewModel.usuarios.observe(this) { usuarios ->
            adapter.updateList(usuarios)
        }

        userViewModel.carregarUsuarios()
    }

    private fun navigateToRegistrar() {
        startActivity(Intent(this, registrarUserActivity::class.java))
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

                    // Salva sessão
                    val sharedPref = getSharedPreferences("user_session", Context.MODE_PRIVATE)
                    with(sharedPref.edit()) {
                        putString("email", user.email)
                        putString("senha", user.password)
                        apply()
                    }

                    startActivity(Intent(this, HomePageUsersActivity::class.java))
                } else {
                    Toast.makeText(this, "Senha incorreta", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
