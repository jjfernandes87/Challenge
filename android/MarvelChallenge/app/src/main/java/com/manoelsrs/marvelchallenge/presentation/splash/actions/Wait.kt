package com.manoelsrs.marvelchallenge.presentation.splash.actions

import io.reactivex.Completable
import io.reactivex.Scheduler
import java.util.concurrent.TimeUnit

class Wait {

    fun execute(duration: Long, scheduler: Scheduler): Completable {
        return Completable.timer(duration, TimeUnit.SECONDS, scheduler)
    }
}