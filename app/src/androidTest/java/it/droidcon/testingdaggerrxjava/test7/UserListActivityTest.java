package it.droidcon.testingdaggerrxjava.test7;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import it.droidcon.testingdaggerrxjava.AsyncTaskSchedulerRule;
import it.droidcon.testingdaggerrxjava.R;
import it.droidcon.testingdaggerrxjava.core.UserInteractor;
import it.droidcon.testingdaggerrxjava.userlist.UserListActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class UserListActivityTest {
    @Rule public final ActivityTestRule<UserListActivity> rule = new ActivityTestRule<>(UserListActivity.class, false, false);

    @Rule public final AsyncTaskSchedulerRule asyncTaskSchedulerRule = new AsyncTaskSchedulerRule();

    @Rule public final MyDaggerMockRule daggerMockRule = new MyDaggerMockRule();

    @Mock UserInteractor userInteractor;

    @Test public void shouldDisplayUsers() {
        rule.launchActivity(null);

        onView(withId(R.id.text)).check(matches(withText("50 user1 - badge1\n\n30 user2 - badge2, badge3")));
    }
}