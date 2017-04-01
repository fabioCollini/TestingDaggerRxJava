package it.droidcon.testingdaggerrxjava.dagger;

import javax.inject.Singleton;

import dagger.Component;
import it.droidcon.testingdaggerrxjava.userlist.UserListComponent;
import it.droidcon.testingdaggerrxjava.userlist.UserListModule;

@Singleton
@Component(modules = {
        UserInteractorModule.class,
        StackOverflowServiceModule.class
})
public interface ApplicationComponent {
    UserListComponent userListComponent(UserListModule module);
}
