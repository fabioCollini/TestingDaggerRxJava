package it.droidcon.testingdaggerrxjava.test2

import com.nhaarman.mockito_kotlin.mock
import dagger.Module
import dagger.Provides
import it.droidcon.testingdaggerrxjava.core.UserInteractor
import javax.inject.Singleton

@Module class TestUserInteractorModule {
    @Provides @Singleton fun provideUserInteractor(): UserInteractor = mock()
}
