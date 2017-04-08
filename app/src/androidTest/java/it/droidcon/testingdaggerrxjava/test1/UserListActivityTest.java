package it.droidcon.testingdaggerrxjava.test1;

import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import it.droidcon.testingdaggerrxjava.AsyncTaskSchedulerRule;
import it.droidcon.testingdaggerrxjava.R;
import it.droidcon.testingdaggerrxjava.core.gson.Badge;
import it.droidcon.testingdaggerrxjava.core.gson.StackOverflowService;
import it.droidcon.testingdaggerrxjava.core.gson.User;
import it.droidcon.testingdaggerrxjava.userlist.UserListActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static it.droidcon.testingdaggerrxjava.TestUtils.getAppFromInstrumentation;
import static org.mockito.Mockito.when;

public class UserListActivityTest {
  @Rule public final ActivityTestRule<UserListActivity> rule =
      new ActivityTestRule<>(UserListActivity.class, false, false);

  @Rule public final AsyncTaskSchedulerRule asyncTaskSchedulerRule = new AsyncTaskSchedulerRule();

  @Inject StackOverflowService stackOverflowService;

  @Before public void setUp() {
    TestApplicationComponent applicationComponent =
        DaggerTestApplicationComponent.create();
    getAppFromInstrumentation().setComponent(applicationComponent);
    applicationComponent.inject(this);
  }

  @Test public void shouldDisplayUsers() {
    when(stackOverflowService.getTopUsers()).thenReturn(Observable.fromArray(
        User.create(1, 50, "user1"),
        User.create(2, 30, "user2")
    ).doOnNext(a -> {
      Thread.sleep(300);
    }).toList());

    when(stackOverflowService.getBadges(1)).thenReturn(
        Single.just(Badge.createList("badge1")));
    when(stackOverflowService.getBadges(2)).thenReturn(
        Single.just(Badge.createList("badge2", "badge3")));

    rule.launchActivity(null);

    onView(withId(R.id.text)).check(matches(withText(
        "50 user1 - badge1\n\n30 user2 - badge2, badge3")));
  }
}