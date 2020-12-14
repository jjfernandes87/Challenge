package br.com.mouzinho.marvelapp.view.main

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import br.com.mouzinho.marvelapp.view.characters.CharactersFragment
import br.com.mouzinho.marvelapp.view.favorites.FavoritesCharactersFragment

class MainViewPagerAdapter(
    fragmentManager: FragmentManager
) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int = 2

    override fun getItem(position: Int) = when (position) {
        1 -> FavoritesCharactersFragment()
        else -> CharactersFragment()
    }
}