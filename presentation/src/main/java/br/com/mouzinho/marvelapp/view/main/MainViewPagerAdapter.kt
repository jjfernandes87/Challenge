package br.com.mouzinho.marvelapp.view.main

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import br.com.mouzinho.marvelapp.R
import br.com.mouzinho.marvelapp.view.characters.CharactersFragment
import br.com.mouzinho.marvelapp.view.favorites.FavoritesCharactersFragment

class MainViewPagerAdapter(
    private val context: Context,
    fragmentManager: FragmentManager
) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int = 2

    override fun getItem(position: Int) = when (position) {
        1 -> FavoritesCharactersFragment()
        else -> CharactersFragment()
    }

    override fun getPageTitle(position: Int) = when(position) {
        1 -> context.getString(R.string.favorites_character_title)
        else -> context.getString(R.string.character_title)
    }
}