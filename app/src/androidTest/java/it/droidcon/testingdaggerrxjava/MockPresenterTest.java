package it.droidcon.testingdaggerrxjava;

import android.support.test.rule.ActivityTestRule;
import it.droidcon.testingdaggerrxjava.test7.MyDaggerMockRule;
import it.droidcon.testingdaggerrxjava.userlist.UserListActivity;
import it.droidcon.testingdaggerrxjava.userlist.UserListPresenter;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.verify;

public class MockPresenterTest {

    @Rule public ActivityTestRule<UserListActivity> rule = new ActivityTestRule<>(UserListActivity.class, false, false);

    @Rule public MyDaggerMockRule daggerMockRule = new MyDaggerMockRule();

    @Mock UserListPresenter presenter;

    @Test
    public void testOnCreate() {
        rule.launchActivity(null);

        onView(withId(R.id.text)).check(matches(withText("")));

        verify(presenter).reloadUserList();
    }
}