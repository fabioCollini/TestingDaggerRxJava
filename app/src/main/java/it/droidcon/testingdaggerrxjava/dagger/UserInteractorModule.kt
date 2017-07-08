package it.droidcon.testingdaggerrxjava.dagger

import dagger.Module
import dagger.Provides
import it.droidcon.testingdaggerrxjava.core.UserInteractor
import it.droidcon.testingdaggerrxjava.core.gson.StackOverflowService
import javax.inject.Singleton

@Module open class UserInteractorModule {

    @Provides @Singleton open fun provideUserInteractor(stackOverflowService: StackOverflowService) =
            UserInteractor(stackOverflowService)
}
