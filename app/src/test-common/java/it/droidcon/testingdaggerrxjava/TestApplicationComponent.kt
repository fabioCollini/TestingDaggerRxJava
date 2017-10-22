package it.droidcon.testingdaggerrxjava

import dagger.Component
import it.droidcon.testingdaggerrxjava.dagger.ApplicationComponent
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(UserInteractorTestModule::class, StackOverflowServiceTestModule::class))
interface TestApplicationComponent: ApplicationComponent {
}