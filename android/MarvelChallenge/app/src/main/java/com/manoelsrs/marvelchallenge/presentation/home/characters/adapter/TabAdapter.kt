package com.manoelsrs.marvelchallenge.presentation.home.characters.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.manoelsrs.marvelchallenge.presentation.home.characters.fragment.CharactersFragment
import com.manoelsrs.marvelchallenge.presentation.home.characters.fragment.FavoritesFragment

class TabAdapter(
    fragmentActivity: FragmentActivity,
    private val charactersFragment: CharactersFragment,
    private val favoritesFragment: FavoritesFragment
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = when(position) {
        0 -> charactersFragment
        else -> favoritesFragment
    }
}