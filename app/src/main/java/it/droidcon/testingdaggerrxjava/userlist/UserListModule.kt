package it.droidcon.testingdaggerrxjava.userlist

import dagger.Module
import dagger.Provides
import it.droidcon.testingdaggerrxjava.core.UserInteractor

@Module class UserListModule(private val activity: UserListActivity) {

    @Provides fun provideActivity() = activity

    @Provides fun providePresenter(interactor: UserInteractor, activity: UserListActivity) =
            UserListPresenter(interactor, activity)
}
