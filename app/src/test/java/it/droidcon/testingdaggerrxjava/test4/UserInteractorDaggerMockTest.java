package it.droidcon.testingdaggerrxjava.test4;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import it.cosenonjaviste.daggermock.DaggerMockRule;
import it.cosenonjaviste.daggermock.InjectFromComponent;
import it.droidcon.testingdaggerrxjava.TestSchedulerRule;
import it.droidcon.testingdaggerrxjava.core.UserInteractor;
import it.droidcon.testingdaggerrxjava.core.UserStats;
import it.droidcon.testingdaggerrxjava.core.gson.Badge;
import it.droidcon.testingdaggerrxjava.core.gson.StackOverflowService;
import it.droidcon.testingdaggerrxjava.core.gson.User;
import it.droidcon.testingdaggerrxjava.dagger.ApplicationComponent;
import it.droidcon.testingdaggerrxjava.dagger.StackOverflowServiceModule;
import it.droidcon.testingdaggerrxjava.dagger.UserInteractorModule;
import it.droidcon.testingdaggerrxjava.userlist.UserListActivity;
import it.droidcon.testingdaggerrxjava.userlist.UserListPresenter;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import static it.droidcon.testingdaggerrxjava.PredicateUtils.check;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.when;

public class UserInteractorDaggerMockTest {
    @Rule public DaggerMockRule<ApplicationComponent> daggerMockRule =
            new DaggerMockRule<>(ApplicationComponent.class, new UserInteractorModule(), new StackOverflowServiceModule())
                    .providesMock(UserListActivity.class);

    @Rule public TestSchedulerRule schedulerRule = new TestSchedulerRule();

    @Mock StackOverflowService stackOverflowService;

    @InjectFromComponent({ UserListActivity.class, UserListPresenter.class }) UserInteractor userInteractor;

    @Test
    public void shouldLoadUsers() throws Exception {
        when(stackOverflowService.getTopUsers()).thenReturn(Observable.fromArray(
                User.create(1, 50, "user1"),
                User.create(2, 30, "user2")
        ).toList());

        when(stackOverflowService.getBadges(1)).thenReturn(
                Single.just(Badge.createList("badge1")).delay(2, TimeUnit.SECONDS));
        when(stackOverflowService.getBadges(2)).thenReturn(
                Single.just(Badge.createList("badge2", "badge3")).delay(1, TimeUnit.SECONDS));

        TestObserver<List<UserStats>> testObserver = userInteractor.loadUsers().test();

        schedulerRule.getTestScheduler().advanceTimeBy(2, TimeUnit.SECONDS);

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