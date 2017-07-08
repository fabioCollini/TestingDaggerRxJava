package it.droidcon.testingdaggerrxjava.test2

import dagger.Module
import dagger.Provides
import it.droidcon.testingdaggerrxjava.core.UserInteractor
import org.mockito.Mockito
import javax.inject.Singleton

@Module class TestUserInteractorModule {
    @Provides @Singleton fun provideUserInteractor(): UserInteractor =
            Mockito.mock(UserInteractor::class.java)
}
