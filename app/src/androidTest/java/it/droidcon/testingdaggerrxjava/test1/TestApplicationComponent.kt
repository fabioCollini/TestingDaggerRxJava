package it.droidcon.testingdaggerrxjava.test1

import dagger.Component
import it.droidcon.testingdaggerrxjava.EspressoUserInteractorModule
import it.droidcon.testingdaggerrxjava.dagger.ApplicationComponent
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(EspressoUserInteractorModule::class, TestStackOverflowServiceModule::class))
interface TestApplicationComponent : ApplicationComponent {
    fun inject(userListActivityTest: UserListActivityTest)
}
