package ui.Home

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xdprototipo.R
import com.example.xdprototipo.databinding.ActivityUtilizadoresBinding
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

        // Navegação
        binding.textViewBack.setOnClickListener { finish() }
        binding.button.setOnClickListener { navigateToRegistrar() }

        // RecyclerView + Adapter
        adapter = UserAdapter(emptyList())
        binding.RecyclerViewUsers.layoutManager = LinearLayoutManager(this)
        binding.RecyclerViewUsers.adapter = adapter

        // ViewModel
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        userViewModel.usuarios.observe(this) { usuarios ->
            adapter.updateList(usuarios)
        }

        // Carregar dados
        userViewModel.carregarUsuarios()
    }

    private fun navigateToRegistrar() {
        startActivity(Intent(this, registrarUserActivity::class.java))
    }
}
