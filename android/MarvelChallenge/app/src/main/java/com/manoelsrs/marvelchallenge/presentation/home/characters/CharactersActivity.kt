package com.manoelsrs.marvelchallenge.presentation.home.characters

import android.os.Bundle
import com.manoelsrs.marvelchallenge.R
import com.manoelsrs.marvelchallenge.core.common.BaseActivity
import dagger.android.AndroidInjection
import javax.inject.Inject

class CharactersActivity : BaseActivity(), CharactersContract {

    @Inject
    lateinit var presenter: CharactersPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_characters)
        AndroidInjection.inject(this)
        presenter.onCreate()
    }
}
