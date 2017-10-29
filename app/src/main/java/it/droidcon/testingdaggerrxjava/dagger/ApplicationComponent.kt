package it.droidcon.testingdaggerrxjava.dagger

import dagger.Component
import it.droidcon.testingdaggerrxjava.userlist.UserListComponent
import it.droidcon.testingdaggerrxjava.userlist.UserListModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(StackOverflowServiceModule::class))
interface ApplicationComponent {
    fun userListComponent(module: UserListModule): UserListComponent
}
