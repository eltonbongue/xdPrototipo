package ui.bindingadapters

import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.xdprototipo.MainActivity
import ui.Home.utilizadoresActivity


fun bindNavigateTo(view: View, destination: Class<out AppCompatActivity>?) {
    view.setOnClickListener {
        destination?.let {
            val context = view.context
            val intent = Intent(context, it)
            context.startActivity(intent)
        }
    }
}