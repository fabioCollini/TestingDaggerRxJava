package it.droidcon.testingdaggerrxjava.test1;

import android.support.test.rule.ActivityTestRule;
import it.droidcon.testingdaggerrxjava.EspressoRule;
import it.droidcon.testingdaggerrxjava.R;
import it.droidcon.testingdaggerrxjava.core.gson.BadgeResponse;
import it.droidcon.testingdaggerrxjava.core.gson.StackOverflowService;
import it.droidcon.testingdaggerrxjava.core.gson.User;
import it.droidcon.testingdaggerrxjava.core.gson.UserResponse;
import it.droidcon.testingdaggerrxjava.userlist.UserListActivity;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static io.reactivex.Single.just;
import static it.droidcon.testingdaggerrxjava.TestUtils.getAppFromInstrumentation;
import static org.mockito.Mockito.when;

public class EndToEndTest {
    @Rule public final ActivityTestRule<UserListActivity> rule = new ActivityTestRule<>(UserListActivity.class, false, false);

    @Rule public final EspressoRule espressoRule = new EspressoRule();

    @Inject StackOverflowService stackOverflowService;

    @Before public void setUp() {
        TestApplicationComponent testApplicationComponent = DaggerTestApplicationComponent.create();
        getAppFromInstrumentation().setComponent(testApplicationComponent);
        testApplicationComponent.inject(this);
    }

    @Test public void shouldDisplayUsers() {
        when(stackOverflowService.getTopUsers()).thenReturn(just(UserResponse.create(
                User.create(1, 50, "user1"),
                User.create(2, 30, "user2")
        )));

        when(stackOverflowService.getBadges(1)).thenReturn(just(BadgeResponse.create("badge1")));
        when(stackOverflowService.getBadges(2)).thenReturn(just(BadgeResponse.create("badge2", "badge3")));

        rule.launchActivity(null);

        onView(withId(R.id.text)).check(matches(withText("50 user1\nbadge1\n\n30 user2\nbadge2, badge3")));
    }
}