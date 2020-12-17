package com.manoelsrs.marvelchallenge.presentation.splash

import android.os.Bundle
import com.manoelsrs.marvelchallenge.R
import com.manoelsrs.marvelchallenge.core.common.BaseActivity
import com.manoelsrs.marvelchallenge.presentation.home.characters.CharactersActivity
import dagger.android.AndroidInjection
import org.jetbrains.anko.startActivity
import javax.inject.Inject

class SplashActivity : BaseActivity(), SplashContract {

    @Inject
    lateinit var presenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        AndroidInjection.inject(this)
        presenter.onCreate()
    }

    override fun navigateToHome() {
        startActivity<CharactersActivity>()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dispose()
    }
}