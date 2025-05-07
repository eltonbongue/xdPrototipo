package ui.Home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.xdprototipo.R
import com.example.xdprototipo.databinding.ActivityPedirBinding
import com.google.android.material.tabs.TabLayout
import ui.adapters.AdapterFragments

class PedirActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPedirBinding.inflate(layoutInflater)
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

        tabLayout = binding.TabLayoutPedir
        viewPager2 = binding.viewPagerPedir

        tabLayout.addTab(tabLayout.newTab().setText("MINHAS"))
        tabLayout.addTab(tabLayout.newTab().setText("TODAS"))
        tabLayout.addTab(tabLayout.newTab().setText("BAR"))
        tabLayout.addTab(tabLayout.newTab().setText("SALA"))
        tabLayout.addTab(tabLayout.newTab().setText("ESPLANADA"))


        val adapter = AdapterFragments(this)
        viewPager2.adapter = adapter

        // Sincroniza ViewPager2 -> TabLayout
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })

        // Sincroniza TabLayout -> ViewPager2
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    viewPager2.currentItem = it.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }
}
