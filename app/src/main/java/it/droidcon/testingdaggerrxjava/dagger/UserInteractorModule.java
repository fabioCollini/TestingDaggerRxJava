package it.droidcon.testingdaggerrxjava.dagger;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import it.droidcon.testingdaggerrxjava.core.UserInteractor;
import it.droidcon.testingdaggerrxjava.core.gson.StackOverflowService;

@Module
public class UserInteractorModule {

    @Provides @Singleton
    public UserInteractor provideUserInteractor(StackOverflowService stackOverflowService) {
        return new UserInteractor(stackOverflowService);
    }
}
