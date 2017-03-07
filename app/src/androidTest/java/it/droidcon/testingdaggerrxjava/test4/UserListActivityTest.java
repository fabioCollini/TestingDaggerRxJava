package it.droidcon.testingdaggerrxjava.test4;

import android.support.test.rule.ActivityTestRule;
import io.reactivex.Observable;
import it.droidcon.testingdaggerrxjava.EspressoRule;
import it.droidcon.testingdaggerrxjava.R;
import it.droidcon.testingdaggerrxjava.core.UserInteractor;
import it.droidcon.testingdaggerrxjava.core.UserStats;
import it.droidcon.testingdaggerrxjava.core.gson.StackOverflowService;
import it.droidcon.testingdaggerrxjava.dagger.ApplicationComponent;
import it.droidcon.testingdaggerrxjava.dagger.DaggerApplicationComponent;
import it.droidcon.testingdaggerrxjava.dagger.UserInteractorModule;
import it.droidcon.testingdaggerrxjava.userlist.UserListActivity;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static it.droidcon.testingdaggerrxjava.TestUtils.getAppFromInstrumentation;
import static org.mockito.Mockito.when;

public class UserListActivityTest {
    @Rule public ActivityTestRule<UserListActivity> rule =
            new ActivityTestRule<>(UserListActivity.class, false, false);

    @Rule public EspressoRule espressoRule = new EspressoRule();

    @Rule public final MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock UserInteractor userInteractor;

    @Before public void setUp() {
        ApplicationComponent component =
                DaggerApplicationComponent.builder()
                        .userInteractorModule(new UserInteractorModule() {
                            @Override
                            public UserInteractor provideUserInteractor(
                                    StackOverflowService stackOverflowService) {
                                return userInteractor;
                            }
                        })
                        .build();
        getAppFromInstrumentation().setComponent(component);
    }

    @Test public void shouldDisplayUsers() {
        when(userInteractor.loadUsers()).thenReturn(
                Observable.fromArray(
                        UserStats.create(1, 50, "user1", "badge1"),
                        UserStats.create(2, 30, "user2", "badge2", "badge3")
                ).toList());

        rule.launchActivity(null);

        onView(withId(R.id.text)).check(matches(withText(
                "50 user1 - badge1\n\n30 user2 - badge2, badge3")));
    }
}