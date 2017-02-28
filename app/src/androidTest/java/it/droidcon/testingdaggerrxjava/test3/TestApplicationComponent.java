package it.droidcon.testingdaggerrxjava.test3;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import it.droidcon.testingdaggerrxjava.dagger.ApplicationComponent;
import it.droidcon.testingdaggerrxjava.dagger.StackOverflowServiceModule;
import it.droidcon.testingdaggerrxjava.dagger.UserInteractorModule;
import it.droidcon.testingdaggerrxjava.userlist.UserListModuleDef;
import javax.inject.Singleton;

@Singleton
@Component(modules = {
    UserInteractorModule.class,
    StackOverflowServiceModule.class,
    AndroidInjectionModule.class,
    UserListModuleDef.class
})
public interface TestApplicationComponent
    extends ApplicationComponent {
  void inject(EndToEndTest endToEndTest);
}
