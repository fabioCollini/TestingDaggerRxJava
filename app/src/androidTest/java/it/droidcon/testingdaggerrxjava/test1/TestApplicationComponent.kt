package it.droidcon.testingdaggerrxjava.test1

import dagger.Component
import it.droidcon.testingdaggerrxjava.dagger.ApplicationComponent
import it.droidcon.testingdaggerrxjava.dagger.UserInteractorModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(UserInteractorModule::class, TestStackOverflowServiceModule::class))
interface TestApplicationComponent : ApplicationComponent {
    fun inject(userListActivityTest: UserListActivityTest)
}
