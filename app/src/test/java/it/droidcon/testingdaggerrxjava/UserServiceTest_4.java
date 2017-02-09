package it.droidcon.testingdaggerrxjava;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import it.droidcon.testingdaggerrxjava.core.UserInteractor;
import it.droidcon.testingdaggerrxjava.core.UserStats;
import it.droidcon.testingdaggerrxjava.core.gson.BadgeResponse;
import it.droidcon.testingdaggerrxjava.core.gson.StackOverflowService;
import it.droidcon.testingdaggerrxjava.core.gson.User;
import it.droidcon.testingdaggerrxjava.core.gson.UserResponse;
import java.util.Arrays;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static io.reactivex.Single.just;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

public class UserServiceTest_4 {

    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @Mock StackOverflowService stackOverflowService;

    @InjectMocks UserInteractor userInteractor;

    @Test public void testSubscribe() {
        when(stackOverflowService.getTopUsers()).thenReturn(
                just(UserResponse.create(
                        User.create(1, 100, "user 1"),
                        User.create(2, 100, "user 2")
                ))
        );
        when(stackOverflowService.getBadges(anyInt())).thenReturn(
                just(BadgeResponse.create("badge")));

        TestObserver<List<UserStats>> testObserver = userInteractor.loadUsers()
                .test();
        testObserver.awaitTerminalEvent();

        testObserver
                .assertNoErrors()
                .assertValue(l -> l.size() == 2)
                .assertValue(l ->
                        Observable.fromIterable(l)
                                .map(UserStats::id)
                                .toList()
                                .blockingGet()
                                .equals(Arrays.asList(1, 2))
                );
    }
}