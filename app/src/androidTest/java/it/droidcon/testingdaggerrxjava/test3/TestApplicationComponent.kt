package it.droidcon.testingdaggerrxjava.test3

import dagger.Component
import it.droidcon.testingdaggerrxjava.dagger.ApplicationComponent
import it.droidcon.testingdaggerrxjava.dagger.StackOverflowServiceModule
import it.droidcon.testingdaggerrxjava.dagger.UserInteractorModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(UserInteractorModule::class, StackOverflowServiceModule::class))
interface TestApplicationComponent : ApplicationComponent {
    fun inject(userListActivityTest: UserListActivityTest)
}
