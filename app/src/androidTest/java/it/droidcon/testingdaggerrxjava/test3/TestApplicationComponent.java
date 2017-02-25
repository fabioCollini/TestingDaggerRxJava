package it.droidcon.testingdaggerrxjava.test3;

import dagger.Component;
import it.droidcon.testingdaggerrxjava.dagger.ApplicationComponent;
import it.droidcon.testingdaggerrxjava.dagger.StackOverflowServiceModule;
import it.droidcon.testingdaggerrxjava.dagger.UserInteractorModule;
import javax.inject.Singleton;

@Singleton
@Component(modules = {
    UserInteractorModule.class,
    StackOverflowServiceModule.class
})
public interface TestApplicationComponent
    extends ApplicationComponent {
  void inject(EndToEndTest endToEndTest);
}
