package it.droidcon.testingdaggerrxjava;

import io.reactivex.observers.TestObserver;
import it.droidcon.testingdaggerrxjava.core.UserInteractor;
import it.droidcon.testingdaggerrxjava.core.UserStats;
import it.droidcon.testingdaggerrxjava.core.gson.StackOverflowService;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static it.droidcon.testingdaggerrxjava.PredicateUtils.check;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class UserServiceTest_1 {

    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @Rule public TestSchedulerRule testSchedulerRule = new TestSchedulerRule();

    @Mock StackOverflowService stackOverflowService;

    @InjectMocks UserInteractor userService;

    @Test public void checkIdsUsingDelays() {
        //when(stackOverflowService.getTopUsers()).thenReturn(
        //        just(create(
        //                User.create(1, 200, "user 1"),
        //                User.create(2, 100, "user 2")
        //        ))
        //);
        //when(stackOverflowService.getBadges(eq(1))).thenReturn(
        //        just(BadgeResponse.create("badge1"))
        //                .delay(2, TimeUnit.SECONDS));
        //when(stackOverflowService.getBadges(eq(2))).thenReturn(
        //        just(BadgeResponse.create("badge2"))
        //                .delay(1, TimeUnit.SECONDS));

        TestObserver<List<UserStats>> testObserver = userService.loadUsers().test();

        testSchedulerRule.getTestScheduler().advanceTimeBy(2, TimeUnit.SECONDS);

        testObserver
                .assertNoErrors()
                .assertValue(check(
                        l -> assertThat(l)
                                .extracting(UserStats::id)
                                .containsExactly(1, 2)
                ));
    }

    @Test
    public void timeout() throws Exception {
        //when(stackOverflowService.getTopUsers()).thenReturn(
        //        just(create(
        //                User.create(1, 200, "user 1"),
        //                User.create(2, 100, "user 2")
        //        )).delay(10, TimeUnit.SECONDS)
        //);
        //when(stackOverflowService.getBadges(eq(1))).thenReturn(
        //        just(BadgeResponse.create("badge1"))
        //                .delay(2, TimeUnit.SECONDS));
        //when(stackOverflowService.getBadges(eq(2))).thenReturn(
        //        just(BadgeResponse.create("badge2"))
        //                .delay(11, TimeUnit.SECONDS));

        TestObserver<List<UserStats>> testObserver = userService.loadUsers().test();

        testSchedulerRule.getTestScheduler().advanceTimeBy(20, TimeUnit.SECONDS);

        testObserver.assertError(TimeoutException.class);
    }
}