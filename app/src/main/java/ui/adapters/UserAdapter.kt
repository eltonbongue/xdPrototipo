package ui.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.xdprototipo.R
import com.example.xdprototipo.databinding.ItemUserBinding
import data.model.User

class UserAdapter(
    private var users: List<User>,
    private val onImageClick: (User) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]

        holder.binding.textNome.text = user.name

        try {
            val uri = Uri.parse(user.photoPath)
            holder.binding.imageViewUsers.setImageURI(uri)
        } catch (e: Exception) {
            holder.binding.imageViewUsers.setImageResource(R.drawable.ic_user_placeholder)
        }

        holder.binding.imageViewUsers.setOnClickListener {
            onImageClick(user)
        }
    }

    override fun getItemCount() = users.size

    fun updateList(novaLista: List<User>) {
        users = novaLista
        notifyDataSetChanged()
    }
}
