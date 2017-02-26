package it.droidcon.testingdaggerrxjava.dagger;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import it.droidcon.testingdaggerrxjava.MyApp;
import it.droidcon.testingdaggerrxjava.userlist.UserListModuleDef;
import javax.inject.Singleton;

@Singleton
@Component(modules = {
    UserInteractorModule.class,
    StackOverflowServiceModule.class,
    AndroidInjectionModule.class,
    UserListModuleDef.class
})
public interface ApplicationComponent {
  void inject(MyApp myApp);
}
