package it.droidcon.testingdaggerrxjava;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class TestSchedulerRule implements TestRule {
  private final TestScheduler testScheduler = new TestScheduler();

  public TestScheduler getTestScheduler() {
    return testScheduler;
  }

  @Override
  public Statement apply(final Statement base, Description d) {
    return new Statement() {
      @Override
      public void evaluate() throws Throwable {
        RxJavaPlugins.setIoSchedulerHandler(
            scheduler -> testScheduler);
        RxJavaPlugins.setComputationSchedulerHandler(
            scheduler -> testScheduler);
        RxJavaPlugins.setNewThreadSchedulerHandler(
            scheduler -> testScheduler);
        RxAndroidPlugins.setMainThreadSchedulerHandler(
            scheduler -> Schedulers.trampoline());

        try {
          base.evaluate();
        } finally {
          RxJavaPlugins.reset();
          RxAndroidPlugins.reset();
        }
      }
    };
  }
}