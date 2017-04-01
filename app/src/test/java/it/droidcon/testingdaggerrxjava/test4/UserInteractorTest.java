package it.droidcon.testingdaggerrxjava.test4;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import it.droidcon.testingdaggerrxjava.TestSchedulerRule;
import it.droidcon.testingdaggerrxjava.core.UserInteractor;
import it.droidcon.testingdaggerrxjava.core.UserStats;
import it.droidcon.testingdaggerrxjava.core.gson.Badge;
import it.droidcon.testingdaggerrxjava.core.gson.StackOverflowService;
import it.droidcon.testingdaggerrxjava.core.gson.User;

import static it.droidcon.testingdaggerrxjava.PredicateUtils.check;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.when;

public class UserInteractorTest {
    @Rule public MockitoRule mockitoRule =
            MockitoJUnit.rule();

    //@Rule public TrampolineSchedulerRule schedulerRule =
    //    new TrampolineSchedulerRule();

    @Rule public TestSchedulerRule schedulerRule =
            new TestSchedulerRule();

    @Mock StackOverflowService stackOverflowService;

    @InjectMocks UserInteractor userInteractor;

    @Test
    public void shouldLoadUsers() throws Exception {
        when(stackOverflowService.getTopUsers()).thenReturn(
                Observable.fromArray(
                        User.create(1, 50, "user1"),
                        User.create(2, 30, "user2")
                ).toList());

        when(stackOverflowService.getBadges(1)).thenReturn(
                Single.just(Badge.createList("badge1"))
                        .delay(2, TimeUnit.SECONDS)
        );
        when(stackOverflowService.getBadges(2)).thenReturn(
                Single.just(Badge.createList("badge2", "badge3"))
                        .delay(1, TimeUnit.SECONDS)
        );

        TestObserver<List<UserStats>> testObserver =
                userInteractor.loadUsers().test();

        schedulerRule.getTestScheduler()
                .advanceTimeBy(2, TimeUnit.SECONDS);

//testObserver
//    .assertNoErrors()
//    .assertValue(Arrays.asList(
//        UserStats.create(1, 50, "user1", "badge1"),
//        UserStats.create(2, 30, "user2", "badge2", "badge3")
//    ));

        testObserver
                .assertNoErrors()
                .assertValue(check(l ->
                        assertThat(l).containsExactly(
                                UserStats.create(1, 50, "user1", "badge1"),
                                UserStats.create(2, 30, "user2", "badge2", "badge3")
                        )
                ));
    }
}