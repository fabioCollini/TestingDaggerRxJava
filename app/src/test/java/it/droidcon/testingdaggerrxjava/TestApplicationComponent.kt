package it.droidcon.testingdaggerrxjava

import dagger.Component
import it.droidcon.testingdaggerrxjava.dagger.ApplicationComponent
import it.droidcon.testingdaggerrxjava.dagger.StackOverflowServiceModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(UserInteractorModule::class, StackOverflowServiceModule::class))
interface TestApplicationComponent : ApplicationComponent {
}