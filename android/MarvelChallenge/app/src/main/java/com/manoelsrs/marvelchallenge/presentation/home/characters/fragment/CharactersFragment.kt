package com.manoelsrs.marvelchallenge.presentation.home.characters.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.manoelsrs.marvelchallenge.R
import com.manoelsrs.marvelchallenge.model.Character
import com.manoelsrs.marvelchallenge.presentation.home.characters.adapter.ItemViewPagerAdapter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_characters.*
import javax.inject.Inject

class CharactersFragment : Fragment(), CharactersFragmentContract {

    @Inject
    lateinit var presenter: CharactersFragmentPresenter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_characters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onCreate()
    }

    override fun updateCharacters(characters: List<Character>) {
        with(rvCharacters) {
            layoutManager = GridLayoutManager(this@CharactersFragment.context, 2)
            adapter = ItemViewPagerAdapter().apply {
                setItems(characters)
            }
        }
    }

    override fun onDetach() {
        presenter.dispose()
        super.onDetach()
    }
}