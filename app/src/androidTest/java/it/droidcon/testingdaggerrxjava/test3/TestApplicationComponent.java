package it.droidcon.testingdaggerrxjava.test3;

import dagger.Component;
import it.droidcon.testingdaggerrxjava.dagger.ApplicationComponent;
import it.droidcon.testingdaggerrxjava.dagger.StackOverflowServiceModule;
import it.droidcon.testingdaggerrxjava.dagger.UserInteractorModule;
import it.droidcon.testingdaggerrxjava.dagger.UtilsModule;
import javax.inject.Singleton;

@Singleton
@Component(modules = {
        UserInteractorModule.class,
        StackOverflowServiceModule.class,
        UtilsModule.class
})
public interface TestApplicationComponent extends ApplicationComponent {
    void inject(EndToEndTest endToEndTest);
}
