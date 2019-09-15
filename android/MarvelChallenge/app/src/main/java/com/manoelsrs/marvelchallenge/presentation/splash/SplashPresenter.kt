package com.manoelsrs.marvelchallenge.presentation.splash

import com.manoelsrs.marvelchallenge.core.common.BasePresenter
import com.manoelsrs.marvelchallenge.presentation.splash.actions.Wait
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SplashPresenter(
    private val contract: SplashContract,
    private val wait: Wait
) : BasePresenter() {

    fun onCreate() {
        wait.execute(2, Schedulers.io())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { contract.navigateToHome() }
            .also { addDisposable(it) }
    }
}