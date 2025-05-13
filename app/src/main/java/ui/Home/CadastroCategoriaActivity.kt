package ui.Home

import CategoriaViewModel
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.xdprototipo.R
import com.example.xdprototipo.databinding.ActivityCadastroCategoriaBinding

class CadastroCategoriaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroCategoriaBinding
    private lateinit var viewModel: CategoriaViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroCategoriaBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        viewModel = ViewModelProvider(this)[CategoriaViewModel::class.java]
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnCadastrarCategoria.setOnClickListener {
            cadastrarCategoria()
        }

    }

    private fun cadastrarCategoria() {
        val nomeCategoria = binding.editNameCategoria.text.toString()
        if (nomeCategoria.isNotEmpty()) {

            Toast.makeText(this, "Categoria cadastrada com sucesso!", Toast.LENGTH_SHORT).show()
            binding.editNameCategoria.text?.clear()
        } else {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
        }

        viewModel.inserirCategoria(nomeCategoria)
    }

}