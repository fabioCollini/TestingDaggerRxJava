package it.droidcon.testingdaggerrxjava;

import io.reactivex.observers.TestObserver;
import it.droidcon.testingdaggerrxjava.core.UserInteractor;
import it.droidcon.testingdaggerrxjava.core.UserStats;
import it.droidcon.testingdaggerrxjava.core.gson.StackOverflowService;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static it.droidcon.testingdaggerrxjava.PredicateUtils.check;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class UserServiceTest_5 {

    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @Mock StackOverflowService stackOverflowService;

    @InjectMocks UserInteractor userInteractor;

    @Test public void testSubscribe() {
        //when(stackOverflowService.getTopUsers()).thenReturn(
        //        just(UserResponse.create(
        //                User.create(1, 100, "user 1"),
        //                User.create(2, 100, "user 2")
        //                )
        //        ));
        //when(stackOverflowService.getBadges(eq(1))).thenReturn(
        //        just(BadgeResponse.create("badge1"))
        //        //.delay(2, TimeUnit.SECONDS)
        //);
        //when(stackOverflowService.getBadges(eq(2))).thenReturn(
        //        just(BadgeResponse.create("badge2"))
        //        //.delay(1, TimeUnit.SECONDS)
        //);

        TestObserver<List<UserStats>> testObserver = userInteractor.loadUsers().test();

        testObserver.awaitTerminalEvent();

        testObserver
                .assertNoErrors()
                .assertValue(check(
                        l -> assertThat(l)
                                .extracting(UserStats::id)
                                .containsExactly(1, 2)
                ));
    }
}