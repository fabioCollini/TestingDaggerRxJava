package it.droidcon.testingdaggerrxjava.userlist

import dagger.Subcomponent

@Subcomponent(modules = arrayOf(UserListModule::class))
interface UserListComponent {
    fun inject(userListActivity: UserListActivity)
}
