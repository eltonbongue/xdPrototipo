package ui.Home

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xdprototipo.R
import com.example.xdprototipo.databinding.ActivityListaItemsBinding
import data.model.User
import data.viewModel.UserViewModel
import ui.adapters.ListaUserAdapter

class ListaItemsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListaItemsBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var adapter: ListaUserAdapter
    private var allUsers: List<User> = emptyList()

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
            val intent = Intent(this, AlterarUsersActivity::class.java).apply {
                putExtra("user_id", user.id)
                putExtra("user_name", user.name)
                putExtra("user_email", user.email)
                putExtra("user_password", user.password)
                putExtra("user_photo", user.photoPath)
            }
            startActivity(intent)
        }

        binding.RecyclerViewListaUsuarios.apply {
            layoutManager = LinearLayoutManager(this@ListaItemsActivity)
            adapter = this@ListaItemsActivity.adapter
            setHasFixedSize(true)
        }

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        userViewModel.usuarios.observe(this) { usuarios ->
            allUsers = usuarios
            adapter.updateList(allUsers)
        }
        userViewModel.carregarUsuarios()

        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().trim().lowercase()
                val filtered = allUsers.filter {
                    it.name.lowercase().contains(query) || it.email.lowercase().contains(query)
                }
                adapter.updateList(filtered)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}




        })


    }
    override fun onResume() {
        super.onResume()
        userViewModel.carregarUsuarios()
    }
}
