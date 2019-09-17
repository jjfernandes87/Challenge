package com.manoelsrs.marvelchallenge.presentation.home.characters

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayoutMediator
import com.manoelsrs.marvelchallenge.R
import com.manoelsrs.marvelchallenge.core.common.BaseActivity
import com.manoelsrs.marvelchallenge.databinding.ActivityCharactersBinding
import com.manoelsrs.marvelchallenge.presentation.home.characters.fragment.adapter.TabAdapter
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_characters.*
import javax.inject.Inject

class CharactersActivity : BaseActivity(), CharactersContract {

    @Inject
    lateinit var presenter: CharactersPresenter

    @Inject
    lateinit var tabAdapter: TabAdapter

    private lateinit var binding: ActivityCharactersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_characters)
        binding.presenter = presenter
        presenter.onCreate()
    }

    override fun configureTabs() {
        vpCharacters.adapter = tabAdapter
        TabLayoutMediator(tabCharacters, vpCharacters) { tab, position ->
            tab.text = when(position) {
                0 -> getString(R.string.characters_text)
                else -> getString(R.string.favorites_text)
            }
        }.attach()
    }

    override fun onDestroy() {
        presenter.dispose()
        super.onDestroy()
    }
}