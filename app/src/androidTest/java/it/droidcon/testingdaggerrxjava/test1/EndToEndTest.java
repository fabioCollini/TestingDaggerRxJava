package it.droidcon.testingdaggerrxjava.test1;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import it.droidcon.testingdaggerrxjava.EspressoSchedulerRule;
import it.droidcon.testingdaggerrxjava.MainActivity;
import it.droidcon.testingdaggerrxjava.MyApp;
import it.droidcon.testingdaggerrxjava.R;
import it.droidcon.testingdaggerrxjava.core.gson.StackOverflowService;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static io.reactivex.Single.just;
import static it.droidcon.testingdaggerrxjava.core.gson.Badge.createBadge;
import static it.droidcon.testingdaggerrxjava.core.gson.BadgeResponse.createBadgeResponse;
import static it.droidcon.testingdaggerrxjava.core.gson.User.createUser;
import static it.droidcon.testingdaggerrxjava.core.gson.UserResponse.createResponse;
import static org.mockito.Mockito.when;

public class EndToEndTest {
    @Rule public final ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class, false, false);

    @Rule public final EspressoSchedulerRule espressoSchedulerRule = new EspressoSchedulerRule();

    @Inject StackOverflowService stackOverflowService;

    @Before
    public void setUp() throws Exception {
        TestApplicationComponent testApplicationComponent = DaggerTestApplicationComponent.create();
        MyApp app = (MyApp) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        app.setComponent(testApplicationComponent);
        testApplicationComponent.inject(this);
    }

    @Test
    public void launchActivity() {
        when(stackOverflowService.getTopUsers()).thenReturn(just(createResponse(
                createUser(1, 10, "user1"),
                createUser(2, 20, "user2")
        )));

        when(stackOverflowService.getBadges(1)).thenReturn(just(createBadgeResponse(createBadge("badge1"))));
        when(stackOverflowService.getBadges(2)).thenReturn(just(createBadgeResponse(createBadge("badge2"), createBadge("badge3"))));

        rule.launchActivity(null);

        onView(withId(R.id.text)).check(matches(withText("10 user1\nbadge1\n\n20 user2\nbadge2, badge3\n\n")));
        System.out.println(123);
    }
}