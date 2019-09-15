package com.manoelsrs.marvelchallenge.presentation.splash

import android.os.Bundle
import com.manoelsrs.marvelchallenge.R
import com.manoelsrs.marvelchallenge.core.common.BaseActivity
import dagger.android.AndroidInjection
import org.jetbrains.anko.toast
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
        toast("Navigate to home")
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dispose()
    }
}