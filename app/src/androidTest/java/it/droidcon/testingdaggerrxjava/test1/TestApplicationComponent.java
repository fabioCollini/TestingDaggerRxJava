package it.droidcon.testingdaggerrxjava.test1;

import dagger.Component;
import it.droidcon.testingdaggerrxjava.dagger.ApplicationComponent;
import it.droidcon.testingdaggerrxjava.dagger.UserInteractorModule;
import javax.inject.Singleton;

@Singleton
@Component(modules = {
    UserInteractorModule.class,
    TestStackOverflowServiceModule.class
})
public interface TestApplicationComponent extends ApplicationComponent {
  void inject(EndToEndTest endToEndTest);
}
