package ui.Home

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.xdprototipo.R
import com.example.xdprototipo.data.datasource.DatabaseHelper
import com.example.xdprototipo.databinding.ActivityCadastrarProdutoBinding
import data.viewModel.ProdutoViewModel

class CadastrarProdutoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastrarProdutoBinding
    private lateinit var viewModel: ProdutoViewModel

    private var categoriaSelecionadaId: Int = 0
    private lateinit var categorias: List<Pair<Int, String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCadastrarProdutoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()

        viewModel = ViewModelProvider(this)[ProdutoViewModel::class.java]

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val dbHelper = DatabaseHelper(this)
        categorias = dbHelper.listarCategorias()

        if (categorias.isEmpty()) {
            Toast.makeText(this, "Nenhuma categoria encontrada.", Toast.LENGTH_LONG).show()
        }

        val nomesCategorias = categorias.map { it.second }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, nomesCategorias)
        binding.ListViewCategoria.adapter = adapter

        binding.ListViewCategoria.setOnItemClickListener { _, _, position, _ ->
            val (id, nome) = categorias[position]
           //binding.textViewCategoriaP.text = nome
            categoriaSelecionadaId = id
            Toast.makeText(this, "Selecionou: $nome", Toast.LENGTH_SHORT).show()
        }

        binding.btnCadastrarProduto.setOnClickListener {
            cadastrarProduto()
        }

        viewModel.mensagem.observe(this) { msg ->
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }

    private fun cadastrarProduto() {
        val nome = binding.editNameProduto.text.toString()
        val precoStr = binding.editPreco.text.toString()
        val categoria = binding.textViewCategoriaP.text.toString()

        if (nome.isNotBlank() && precoStr.isNotBlank() && categoria.isNotBlank()) {
            val preco: Double = try {
                precoStr.replace(",", ".").toDouble()
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Preço inválido!", Toast.LENGTH_SHORT).show()
                return
            }

            try {
                viewModel.inserirProduto(0, nome, preco, categoriaSelecionadaId)
                Toast.makeText(this, "Produto cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                finish()
            } catch (e: Exception) {
                Toast.makeText(this, "Erro ao cadastrar: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
        }
    }
}
