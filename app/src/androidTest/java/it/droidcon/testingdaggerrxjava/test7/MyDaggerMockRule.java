package it.droidcon.testingdaggerrxjava.test7;

import android.support.test.InstrumentationRegistry;
import io.reactivex.Single;
import it.cosenonjaviste.daggermock.DaggerMockRule;
import it.droidcon.testingdaggerrxjava.MyApp;
import it.droidcon.testingdaggerrxjava.core.UserInteractor;
import it.droidcon.testingdaggerrxjava.core.gson.StackOverflowService;
import it.droidcon.testingdaggerrxjava.dagger.ApplicationComponent;
import it.droidcon.testingdaggerrxjava.dagger.UserInteractorModule;
import java.util.Arrays;

import static it.droidcon.testingdaggerrxjava.core.UserStats.createUserStats;
import static it.droidcon.testingdaggerrxjava.core.gson.Badge.createBadge;
import static it.droidcon.testingdaggerrxjava.core.gson.User.createUser;
import static org.mockito.Mockito.when;

public class MyDaggerMockRule extends DaggerMockRule<ApplicationComponent> {
    public MyDaggerMockRule() {
        super(ApplicationComponent.class, new UserInteractorModule());
        set(component -> {
            MyApp app = (MyApp) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
            app.setComponent(component);
        });
        providesMock(StackOverflowService.class);
        providesMock(UserInteractor.class, mock ->
                when(mock.loadUsers()).thenReturn(Single.just(Arrays.asList(
                        createUserStats(createUser(1, 10, "user1"), createBadge("badge1")),
                        createUserStats(createUser(2, 20, "user2"), createBadge("badge2"), createBadge("badge3"))
                )))
        );
    }
}
