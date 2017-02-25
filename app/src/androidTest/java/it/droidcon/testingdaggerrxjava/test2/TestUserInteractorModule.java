package it.droidcon.testingdaggerrxjava.test2;

import dagger.Module;
import dagger.Provides;
import it.droidcon.testingdaggerrxjava.core.UserInteractor;
import javax.inject.Singleton;
import org.mockito.Mockito;

@Module
public class TestUserInteractorModule {
  @Provides @Singleton
  public UserInteractor provideUserInteractor() {
    return Mockito.mock(UserInteractor.class);
  }
}
