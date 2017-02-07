package it.droidcon.testingdaggerrxjava.test1;

import dagger.Module;
import dagger.Provides;
import it.droidcon.testingdaggerrxjava.core.gson.StackOverflowService;
import javax.inject.Singleton;
import org.mockito.Mockito;

@Module
public class TestStackOverflowServiceModule {
    @Provides @Singleton public StackOverflowService provideStackOverflowService() {
        return Mockito.mock(StackOverflowService.class);
    }
}
