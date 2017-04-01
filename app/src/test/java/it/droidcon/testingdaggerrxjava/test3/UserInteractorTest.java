package it.droidcon.testingdaggerrxjava.test3;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import it.droidcon.testingdaggerrxjava.core.UserInteractor;
import it.droidcon.testingdaggerrxjava.core.UserStats;
import it.droidcon.testingdaggerrxjava.core.gson.Badge;
import it.droidcon.testingdaggerrxjava.core.gson.StackOverflowService;
import it.droidcon.testingdaggerrxjava.core.gson.User;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.when;

public class UserInteractorTest {
    @Rule public MockitoRule mockitoRule =
            MockitoJUnit.rule();

    //@Rule public TrampolineSchedulerRule schedulerRule =
    //    new TrampolineSchedulerRule();

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
                //.doOnSuccess(l->Thread.sleep(100))
        );
        when(stackOverflowService.getBadges(2)).thenReturn(
                Single.just(Badge.createList("badge2", "badge3"))
        );

        List<UserStats> users = userInteractor.loadUsers().blockingGet();
        assertThat(users).containsExactly(
                UserStats.create(1, 50, "user1", "badge1"),
                UserStats.create(2, 30, "user2", "badge2", "badge3"));

        //userInteractor.loadUsers()
        //    .test()
        //    .assertNoErrors()
        //    .assertComplete()
        //    .assertValue(Arrays.asList(
        //        UserStats.create(1, 50, "user1", "badge1"),
        //        UserStats.create(2, 30, "user2", "badge2", "badge3")
        //    ));

        //userInteractor.loadUsers()
        //    .test()
        //    .assertNoErrors()
        //    .assertValue(check(l ->
        //        assertThat(l).containsExactly(
        //            UserStats.create(1, 50, "user1", "badge1"),
        //            UserStats.create(2, 30, "user2", "badge2", "badge3")
        //        )
        //    ));
    }
}