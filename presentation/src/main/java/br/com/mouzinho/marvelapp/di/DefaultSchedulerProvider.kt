package br.com.mouzinho.marvelapp.di

import br.com.mouzinho.domain.scheduler.SchedulerProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DefaultSchedulerProvider @Inject constructor() : SchedulerProvider {
    override fun io() = Schedulers.io()

    override fun main() = AndroidSchedulers.mainThread()

    override fun computation() = Schedulers.computation()
}