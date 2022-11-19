package com.manoelsrs.marvelchallenge.presentation.splash.actions

import io.reactivex.schedulers.TestScheduler
import org.junit.Test
import java.util.concurrent.TimeUnit

class WaitTest {

    private val wait = Wait()

    @Test
    fun `should wait 2 seconds`() {
        val scheduler = TestScheduler()

        val testSubscriber = wait.execute(2, scheduler)
            .subscribeOn(scheduler)
            .test()

        scheduler.advanceTimeTo(2, TimeUnit.SECONDS)
        testSubscriber.assertComplete()
    }

    @Test
    fun `should not complete before 5 seconds`() {
        val scheduler = TestScheduler()

        val testSubscriber = wait.execute(5, scheduler)
            .subscribeOn(scheduler)
            .test()

        scheduler.advanceTimeTo(4999, TimeUnit.MILLISECONDS)
        testSubscriber.assertNotComplete()
    }
}