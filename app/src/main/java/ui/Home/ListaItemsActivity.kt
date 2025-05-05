package ui.Home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xdprototipo.R
import com.example.xdprototipo.databinding.ActivityListaItemsBinding
import data.viewModel.UserViewModel
import ui.adapters.ListaUserAdapter

class ListaItemsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListaItemsBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var adapter: ListaUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListaItemsBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val sys = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(sys.left, sys.top, sys.right, sys.bottom)
            insets
        }


        adapter = ListaUserAdapter(emptyList()) { user ->
        }

        binding.RecyclerViewListaUsuarios.apply {
            layoutManager = LinearLayoutManager(this@ListaItemsActivity)
            adapter = this@ListaItemsActivity.adapter
            setHasFixedSize(true)
        }


        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        userViewModel.usuarios.observe(this) { usuarios ->

            adapter.updateList(usuarios)
        }
        userViewModel.carregarUsuarios()
    }
}
