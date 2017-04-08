package it.droidcon.testingdaggerrxjava.test6;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import io.reactivex.Observable;
import it.droidcon.testingdaggerrxjava.AsyncTaskSchedulerRule;
import it.droidcon.testingdaggerrxjava.R;
import it.droidcon.testingdaggerrxjava.core.UserInteractor;
import it.droidcon.testingdaggerrxjava.core.UserStats;
import it.droidcon.testingdaggerrxjava.userlist.UserListActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.when;

public class UserListActivityTest {
  @Rule public ActivityTestRule<UserListActivity> rule =
      new ActivityTestRule<>(UserListActivity.class, false, false);

  @Rule public AsyncTaskSchedulerRule asyncTaskSchedulerRule = new AsyncTaskSchedulerRule();

  @Rule public MyDaggerMockRule daggerMockRule = new MyDaggerMockRule();

  @Mock UserInteractor userInteractor;

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