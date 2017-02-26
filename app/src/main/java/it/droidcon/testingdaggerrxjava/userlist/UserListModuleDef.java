package it.droidcon.testingdaggerrxjava.userlist;

import android.app.Activity;
import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module(subcomponents = UserListComponent.class)
public abstract class UserListModuleDef {
  @Binds
  @IntoMap
  @ActivityKey(UserListActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindYourActivityInjectorFactory(UserListComponent.Builder builder);
}
