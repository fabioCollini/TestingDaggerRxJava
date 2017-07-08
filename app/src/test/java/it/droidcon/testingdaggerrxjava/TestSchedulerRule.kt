package it.droidcon.testingdaggerrxjava

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class TestSchedulerRule : TestWatcher() {
    val testScheduler = TestScheduler()

    override fun starting(description: Description?) {
        RxJavaPlugins.setIoSchedulerHandler { testScheduler }
        RxJavaPlugins.setComputationSchedulerHandler { testScheduler }
        RxJavaPlugins.setNewThreadSchedulerHandler { testScheduler }
        RxAndroidPlugins.setMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    override fun finished(description: Description?) {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }
}