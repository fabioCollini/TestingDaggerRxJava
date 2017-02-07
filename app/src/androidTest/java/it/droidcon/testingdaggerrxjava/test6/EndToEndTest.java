package it.droidcon.testingdaggerrxjava.test6;

import android.support.test.rule.ActivityTestRule;
import io.reactivex.Single;
import it.droidcon.testingdaggerrxjava.EspressoSchedulerRule;
import it.droidcon.testingdaggerrxjava.MainActivity;
import it.droidcon.testingdaggerrxjava.R;
import it.droidcon.testingdaggerrxjava.core.UserInteractor;
import java.util.Arrays;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static it.droidcon.testingdaggerrxjava.core.UserStats.createUserStats;
import static it.droidcon.testingdaggerrxjava.core.gson.Badge.createBadge;
import static it.droidcon.testingdaggerrxjava.core.gson.User.createUser;
import static org.mockito.Mockito.when;

public class EndToEndTest {
    @Rule public final ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class, false, false);

    @Rule public final EspressoSchedulerRule espressoSchedulerRule = new EspressoSchedulerRule();

    @Rule public final MyDaggerMockRule daggerMockRule = new MyDaggerMockRule();

    @Mock UserInteractor userInteractor;

    @Test
    public void launchActivity() {
        when(userInteractor.loadUsers()).thenReturn(Single.just(Arrays.asList(
                createUserStats(createUser(1, 10, "user1"), createBadge("badge1")),
                createUserStats(createUser(2, 20, "user2"), createBadge("badge2"), createBadge("badge3"))
        )));

        rule.launchActivity(null);

        onView(withId(R.id.text)).check(matches(withText("10 user1\nbadge1\n\n20 user2\nbadge2, badge3\n\n")));
    }
}