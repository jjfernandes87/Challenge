package br.com.mouzinho.marvelapp.di

import br.com.mouzinho.domain.scheduler.SchedulerProvider
import io.reactivex.schedulers.Schedulers

class TestSchedulerProvider : SchedulerProvider {

    override fun io() = Schedulers.trampoline()

    override fun main() = Schedulers.trampoline()

    override fun computation() = Schedulers.trampoline()
}