package paixao.leonardo.marvel.heroes.feature.core.utils

import com.google.android.material.tabs.TabLayout

class OnTabSelectedListener(private val onTabSelected: (position: Int) -> Unit) : TabLayout.OnTabSelectedListener {
    override fun onTabReselected(p0: TabLayout.Tab?) {}

    override fun onTabUnselected(p0: TabLayout.Tab?) {}

    override fun onTabSelected(tag: TabLayout.Tab?) {
        onTabSelected(tag?.position ?: 0)
    }
}
