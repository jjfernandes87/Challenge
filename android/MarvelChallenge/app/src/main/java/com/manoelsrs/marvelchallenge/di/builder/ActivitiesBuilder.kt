package com.manoelsrs.marvelchallenge.di.builder

import com.manoelsrs.marvelchallenge.di.PerActivity
import com.manoelsrs.marvelchallenge.di.module.SplashActivityModule
import com.manoelsrs.marvelchallenge.presentation.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesBuilder {

    @PerActivity
    @ContributesAndroidInjector(modules = [(SplashActivityModule::class)])
    abstract fun bindSplashActivity(): SplashActivity
}