package com.manoelsrs.marvelchallenge.di.module

import com.manoelsrs.marvelchallenge.di.PerActivity
import com.manoelsrs.marvelchallenge.presentation.splash.SplashActivity
import com.manoelsrs.marvelchallenge.presentation.splash.SplashPresenter
import com.manoelsrs.marvelchallenge.presentation.splash.actions.Wait
import dagger.Module
import dagger.Provides

@Module
class SplashActivityModule {

    @PerActivity
    @Provides
    fun providesSplashPresenter(activity: SplashActivity, wait: Wait) =
        SplashPresenter(activity, wait)

    @Provides
    fun providesWaitAction(): Wait = Wait()
}