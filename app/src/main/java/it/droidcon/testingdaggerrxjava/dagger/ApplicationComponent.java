package it.droidcon.testingdaggerrxjava.dagger;

import dagger.Component;
import it.droidcon.testingdaggerrxjava.userlist.UserListComponent;
import it.droidcon.testingdaggerrxjava.userlist.UserListModule;
import javax.inject.Singleton;

@Singleton
@Component(modules = {
        UserInteractorModule.class,
        StackOverflowServiceModule.class,
        UtilsModule.class
})
public interface ApplicationComponent {
    UserListComponent userListComponent(UserListModule module);
}
