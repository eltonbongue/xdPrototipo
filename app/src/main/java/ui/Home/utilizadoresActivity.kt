package ui.Home

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.xdprototipo.MainActivity
import com.example.xdprototipo.R
import com.example.xdprototipo.databinding.ActivityUtilizadoresBinding

class utilizadoresActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUtilizadoresBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUtilizadoresBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

      binding.textViewBack.setOnClickListener {
          finish()
        }



    }


}