package it.droidcon.testingdaggerrxjava.test3;

import io.reactivex.Observable;
import it.cosenonjaviste.daggermock.DaggerMockRule;
import it.cosenonjaviste.daggermock.InjectFromComponent;
import it.droidcon.testingdaggerrxjava.TrampolineSchedulerRule;
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
import java.util.Arrays;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import static it.droidcon.testingdaggerrxjava.PredicateUtils.check;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.when;

public class UserInteractorTest {
    @Rule public DaggerMockRule<ApplicationComponent> daggerMockRule =
            new DaggerMockRule<>(ApplicationComponent.class, new UserInteractorModule(), new StackOverflowServiceModule())
                    .providesMock(UserListActivity.class);

    @Rule public TrampolineSchedulerRule schedulerRule = new TrampolineSchedulerRule();

    @Mock StackOverflowService stackOverflowService;

    @InjectFromComponent({ UserListActivity.class, UserListPresenter.class }) UserInteractor userInteractor;

    @Test
    public void shouldLoadUsers() throws Exception {
        when(stackOverflowService.getTopUsers()).thenReturn(Observable.fromArray(
                User.create(1, 50, "user1"),
                User.create(2, 30, "user2")
        ).toList());

        when(stackOverflowService.getBadges(1)).thenReturn(
                Observable.fromArray(Badge.create("badge1")).toList());
        when(stackOverflowService.getBadges(2)).thenReturn(
                Observable.fromArray(Badge.create("badge2"), Badge.create("badge3")).toList());

        userInteractor.loadUsers()
                .test()
                .assertNoErrors()
                .assertValue(Arrays.asList(
                        UserStats.create(1, 50, "user1", "badge1"),
                        UserStats.create(2, 30, "user2", "badge2", "badge3")
                ));

        userInteractor.loadUsers()
                .test()
                .assertNoErrors()
                .assertValue(check(l ->
                        assertThat(l).containsExactly(
                                UserStats.create(1, 50, "user1", "badge1"),
                                UserStats.create(2, 30, "user2", "badge2", "badge3")
                        )
                ));
    }
}