package it.droidcon.testingdaggerrxjava.userlist;

import dagger.Module;
import dagger.Provides;
import it.droidcon.testingdaggerrxjava.core.UserInteractor;

@Module
public class UserListModule {
    private UserListActivity activity;

    public UserListModule(UserListActivity activity) {
        this.activity = activity;
    }

    @Provides public UserListActivity provideActivity() {
        return activity;
    }

    @Provides
    public UserListPresenter providePresenter(UserInteractor interactor, UserListActivity activity) {
        return new UserListPresenter(interactor, activity);
    }
}
