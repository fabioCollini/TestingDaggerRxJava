package it.droidcon.testingdaggerrxjava.test2;

import android.support.test.rule.ActivityTestRule;
import io.reactivex.Observable;
import it.droidcon.testingdaggerrxjava.EspressoRule;
import it.droidcon.testingdaggerrxjava.MainActivity;
import it.droidcon.testingdaggerrxjava.R;
import it.droidcon.testingdaggerrxjava.core.UserInteractor;
import it.droidcon.testingdaggerrxjava.core.UserStats;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static it.droidcon.testingdaggerrxjava.TestUtils.getAppFromInstrumentation;
import static org.mockito.Mockito.when;

public class EndToEndTest {
    @Rule public final ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class, false, false);

    @Rule public final EspressoRule espressoRule = new EspressoRule();

    @Inject UserInteractor userInteractor;

    @Before public void setUp() {
        TestApplicationComponent testApplicationComponent = DaggerTestApplicationComponent.create();
        getAppFromInstrumentation().setComponent(testApplicationComponent);
        testApplicationComponent.inject(this);
    }

    @Test public void shouldDisplayUsers() {
        when(userInteractor.loadUsers()).thenReturn(Observable.fromArray(
                UserStats.create(1, 50, "user1", "badge1"),
                UserStats.create(2, 30, "user2", "badge2", "badge3")
        ).toList());

        rule.launchActivity(null);

        onView(withId(R.id.text)).check(matches(withText("50 user1\nbadge1\n\n30 user2\nbadge2, badge3")));
    }
}