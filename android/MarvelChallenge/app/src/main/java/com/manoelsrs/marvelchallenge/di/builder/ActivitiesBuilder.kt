package com.manoelsrs.marvelchallenge.di.builder

import com.manoelsrs.marvelchallenge.di.PerActivity
import com.manoelsrs.marvelchallenge.di.PerFragment
import com.manoelsrs.marvelchallenge.di.module.CharactersActivityModule
import com.manoelsrs.marvelchallenge.di.module.CharactersFragmentModule
import com.manoelsrs.marvelchallenge.di.module.SplashActivityModule
import com.manoelsrs.marvelchallenge.presentation.home.characters.CharactersActivity
import com.manoelsrs.marvelchallenge.presentation.home.characters.fragment.CharactersFragment
import com.manoelsrs.marvelchallenge.presentation.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesBuilder {

    @PerActivity
    @ContributesAndroidInjector(modules = [(SplashActivityModule::class)])
    abstract fun bindSplashActivity(): SplashActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [(CharactersActivityModule::class)])
    abstract fun bindCharactersActivity(): CharactersActivity

    @PerFragment
    @ContributesAndroidInjector(modules = [(CharactersFragmentModule::class)])
    abstract fun bindCharactersFragment(): CharactersFragment
//
//    @PerFragment
//    @ContributesAndroidInjector(modules = [(FavoritesFragmentModule::class)])
//    abstract fun bindFavoritesFragment(): FavoritesFragment
}