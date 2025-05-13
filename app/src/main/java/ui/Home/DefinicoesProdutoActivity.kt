package ui.Home

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.xdprototipo.R
import com.example.xdprototipo.databinding.ActivityDefinicoesCadastroProdutosBinding

class DefinicoesProdutoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDefinicoesCadastroProdutosBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDefinicoesCadastroProdutosBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.buttonCadastrarProduto .setOnClickListener {
            startActivity(Intent(this, CadastrarProdutoActivity::class.java))
        }

        binding.buttonCadastroCategoria .setOnClickListener {
            startActivity(Intent(this, CadastroCategoriaActivity::class.java))
        }
        binding.textViewBack.setOnClickListener {
            finish()
        }
    }
}