package it.droidcon.testingdaggerrxjava.test1

import com.nhaarman.mockito_kotlin.mock
import dagger.Module
import dagger.Provides
import it.droidcon.testingdaggerrxjava.core.gson.StackOverflowService
import javax.inject.Singleton

@Module class TestStackOverflowServiceModule {
    @Provides @Singleton fun provideStackOverflowService(): StackOverflowService = mock()
}
