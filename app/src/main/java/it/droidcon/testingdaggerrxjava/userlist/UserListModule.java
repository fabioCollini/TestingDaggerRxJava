package it.droidcon.testingdaggerrxjava.userlist;

import dagger.Module;
import dagger.Provides;

@Module
public class UserListModule {
    private UserListActivity activity;

    public UserListModule(UserListActivity activity) {
        this.activity = activity;
    }

    @Provides public UserListActivity provideActivity() {
        return activity;
    }
}
