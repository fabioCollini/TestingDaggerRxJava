package it.droidcon.testingdaggerrxjava.userlist;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent(modules = UserListModule.class)
public interface UserListComponent extends AndroidInjector<UserListActivity> {
  @Subcomponent.Builder
  abstract class Builder extends AndroidInjector.Builder<UserListActivity> {
  }
}
