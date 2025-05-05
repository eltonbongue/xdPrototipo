package ui.Home

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.xdprototipo.R
import ui.Home.ListaItemsActivity

import com.example.xdprototipo.databinding.ActivityDefinicoesUsersBinding

class DefinicoesUsersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDefinicoesUsersBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDefinicoesUsersBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.buttonRegistrar.setOnClickListener { navigateToRegistrar() }
        binding.buttonEditar.setOnClickListener { navigateToEditar() }
    }


    private fun navigateToRegistrar() {
        startActivity(Intent(this, registrarUserActivity::class.java))
    }

    private fun navigateToEditar() {
        startActivity(Intent(this, ListaItemsActivity::class.java))
    }


}