package it.droidcon.testingdaggerrxjava.test2;

import dagger.Component;
import it.droidcon.testingdaggerrxjava.dagger.ApplicationComponent;
import it.droidcon.testingdaggerrxjava.dagger.StackOverflowServiceModule;
import it.droidcon.testingdaggerrxjava.dagger.UtilsModule;
import javax.inject.Singleton;

@Singleton
@Component(modules = {
        TestUserInteractorModule.class,
        StackOverflowServiceModule.class,
        UtilsModule.class
})
public interface TestApplicationComponent extends ApplicationComponent {
    void inject(EndToEndTest endToEndTest);
}