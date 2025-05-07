package ui.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.xdprototipo.R
import com.example.xdprototipo.databinding.ItemListaUsersBinding
import data.model.User

class ListaUserAdapter(
    private var users: List<User>,
    private val onItemClick: (User) -> Unit
) : RecyclerView.Adapter<ListaUserAdapter.UserViewHolder>() {

    inner class UserViewHolder(val binding: ItemListaUsersBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListaUsersBinding.inflate(inflater, parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]

        holder.binding.textViewNome.text = user.name
        holder.binding.textViewEmail.text = user.email

        // Preenche imagem (ou placeholder)
        try {
            val uri = Uri.parse(user.photoPath)
            holder.binding.imageViewUsuarios.setImageURI(uri)
        } catch (e: Exception) {
            holder.binding.imageViewUsuarios.setImageResource(R.drawable.ic_user_placeholder)
        }


        holder.itemView.setOnClickListener {
            onItemClick(user)
        }
    }

    override fun getItemCount() = users.size

    fun updateList(novaLista: List<User>) {
        users = novaLista
        notifyDataSetChanged()
    }
}
