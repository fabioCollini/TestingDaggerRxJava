package it.droidcon.testingdaggerrxjava.test2

import dagger.Component
import it.droidcon.testingdaggerrxjava.dagger.ApplicationComponent
import it.droidcon.testingdaggerrxjava.dagger.StackOverflowServiceModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(TestUserInteractorModule::class, StackOverflowServiceModule::class))
interface TestApplicationComponent : ApplicationComponent {
    fun inject(userListActivityTest: UserListActivityTest)
}
