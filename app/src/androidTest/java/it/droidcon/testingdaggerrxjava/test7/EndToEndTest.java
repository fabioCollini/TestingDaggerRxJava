package it.droidcon.testingdaggerrxjava.test7;

import android.support.test.rule.ActivityTestRule;
import it.droidcon.testingdaggerrxjava.EspressoRule;
import it.droidcon.testingdaggerrxjava.MainActivity;
import it.droidcon.testingdaggerrxjava.R;
import it.droidcon.testingdaggerrxjava.core.UserInteractor;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class EndToEndTest {
    @Rule public final ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class, false, false);

    @Rule public final EspressoRule espressoRule = new EspressoRule();

    @Rule public final MyDaggerMockRule daggerMockRule = new MyDaggerMockRule();

    @Mock UserInteractor userInteractor;

    @Test public void shouldDisplayUsers() {
        rule.launchActivity(null);

        onView(withId(R.id.text)).check(matches(withText("50 user1\nbadge1\n\n30 user2\nbadge2, badge3")));
    }
}