package com.example.xdprototipo

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.xdprototipo.databinding.ActivityMainBinding
import ui.Home.definicoesActivity
import ui.Home.inicializarActivity
import ui.Home.planosEprecosActivity
import ui.Home.utilizadoresActivity
import ui.Home.xdOrdersActivity


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // TODO: CORRETO: os Listeners ficam DENTRO do onCreate
        binding.textViewUtilizadores.setOnClickListener {
            navigateToUtilizadores()
        }

        binding.textViewDefinicoes.setOnClickListener {
            navigateToDefinicoes()
        }

        binding.textViewXdVideos.setOnClickListener {
         navigateToXdVideos()
        }

        binding.textViewInicializar.setOnClickListener {
            navigateToInicializar()
        }

        binding.textViewPlanosPrecos.setOnClickListener {
            navigateToPlanosPrecos()
        }

        binding.textViewXdOrders.setOnClickListener {
            navigateToxdOrders()
        }
    }

    private fun navigateToUtilizadores() {
        val intent = Intent(this, utilizadoresActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToDefinicoes() {
        val intent = Intent(this, definicoesActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToXdVideos() {
        val intent = Intent(this, xdVideosActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToInicializar() {
        val intent = Intent(this, inicializarActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToPlanosPrecos() {
        val intent = Intent(this, planosEprecosActivity()::class.java)
        startActivity(intent)
    }
    private fun navigateToxdOrders() {
        val intent = Intent(this, xdOrdersActivity::class.java)
        startActivity(intent)
    }

}
