package it.droidcon.testingdaggerrxjava

import com.github.tmurakami.dexopener.DexOpenerAndroidJUnitRunner
import com.squareup.rx2.idler.Rx2Idler
import io.reactivex.plugins.RxJavaPlugins

class MyTestRunner: DexOpenerAndroidJUnitRunner() {
  override fun onStart() {
    RxJavaPlugins.setInitComputationSchedulerHandler(
        Rx2Idler.create("RxJava 2.x Computation Scheduler"))
    RxJavaPlugins.setInitIoSchedulerHandler(
        Rx2Idler.create("RxJava 2.x Io Scheduler"))
    RxJavaPlugins.setInitNewThreadSchedulerHandler(
        Rx2Idler.create("RxJava 2.x NewThread Scheduler"))

    super.onStart()
  }
}