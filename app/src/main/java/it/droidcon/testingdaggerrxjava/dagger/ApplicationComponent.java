package it.droidcon.testingdaggerrxjava.dagger;

import dagger.Component;
import it.droidcon.testingdaggerrxjava.MainActivity;
import javax.inject.Singleton;

@Singleton
@Component(modules = {
        UserInteractorModule.class,
        StackOverflowServiceModule.class,
        UtilsModule.class
})
public interface ApplicationComponent {
    void inject(MainActivity mainActivity);
}
