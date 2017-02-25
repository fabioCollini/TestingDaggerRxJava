package it.droidcon.testingdaggerrxjava.test2;

import dagger.Component;
import it.droidcon.testingdaggerrxjava.dagger.ApplicationComponent;
import it.droidcon.testingdaggerrxjava.dagger.StackOverflowServiceModule;
import javax.inject.Singleton;

@Singleton
@Component(modules = {
    TestUserInteractorModule.class,
    StackOverflowServiceModule.class
})
public interface TestApplicationComponent
    extends ApplicationComponent {
  void inject(EndToEndTest endToEndTest);
}
