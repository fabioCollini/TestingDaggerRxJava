package it.droidcon.testingdaggerrxjava.test1

import dagger.Module
import dagger.Provides
import it.droidcon.testingdaggerrxjava.core.gson.StackOverflowService
import org.mockito.Mockito
import javax.inject.Singleton

@Module class TestStackOverflowServiceModule {
    @Provides @Singleton fun provideStackOverflowService(): StackOverflowService =
            Mockito.mock(StackOverflowService::class.java)
}
