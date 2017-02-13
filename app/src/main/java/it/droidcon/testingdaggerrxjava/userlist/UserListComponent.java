package it.droidcon.testingdaggerrxjava.userlist;

import dagger.Subcomponent;

@Subcomponent(modules = UserListModule.class)
public interface UserListComponent {
    void inject(UserListActivity userListActivity);
}
