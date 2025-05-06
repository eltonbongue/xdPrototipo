package ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ui.fragments.BarFragment
import ui.fragments.EsplanadaFragment
import ui.fragments.MyDeskFragment
import ui.fragments.SalaFragment
import ui.fragments.TodasFragment

class AdapterFragments(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MyDeskFragment()
            1 -> TodasFragment()
            2 -> BarFragment()
            3 -> SalaFragment()
            4 -> EsplanadaFragment()
            else -> throw IndexOutOfBoundsException("Invalid tab position")
        }
    }
}
