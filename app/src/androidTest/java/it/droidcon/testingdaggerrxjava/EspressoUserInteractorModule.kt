package it.droidcon.testingdaggerrxjava

import dagger.Module
import dagger.Provides
import it.droidcon.testingdaggerrxjava.core.UserInteractor
import it.droidcon.testingdaggerrxjava.core.gson.StackOverflowService
import javax.inject.Singleton

@Module open class EspressoUserInteractorModule {

    @Provides
    @Singleton open fun provideUserInteractor(stackOverflowService: StackOverflowService) =
            UserInteractor(stackOverflowService)
}