package it.droidcon.testingdaggerrxjava.test3;

import it.cosenonjaviste.daggermock.DaggerMockRule;
import it.cosenonjaviste.daggermock.InjectFromComponent;
import it.droidcon.testingdaggerrxjava.TrampolineSchedulerRule;
import it.droidcon.testingdaggerrxjava.core.UserInteractor;
import it.droidcon.testingdaggerrxjava.core.UserStats;
import it.droidcon.testingdaggerrxjava.core.gson.BadgeResponse;
import it.droidcon.testingdaggerrxjava.core.gson.StackOverflowService;
import it.droidcon.testingdaggerrxjava.core.gson.User;
import it.droidcon.testingdaggerrxjava.core.gson.UserResponse;
import it.droidcon.testingdaggerrxjava.dagger.ApplicationComponent;
import it.droidcon.testingdaggerrxjava.dagger.StackOverflowServiceModule;
import it.droidcon.testingdaggerrxjava.dagger.UserInteractorModule;
import it.droidcon.testingdaggerrxjava.userlist.UserListActivity;
import it.droidcon.testingdaggerrxjava.userlist.UserListPresenter;
import java.util.Arrays;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import static io.reactivex.Single.just;
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
        when(stackOverflowService.getTopUsers()).thenReturn(just(UserResponse.create(
                User.create(1, 50, "user1"),
                User.create(2, 30, "user2")
        )));

        when(stackOverflowService.getBadges(1)).thenReturn(just(BadgeResponse.create("badge1")));
        when(stackOverflowService.getBadges(2)).thenReturn(just(BadgeResponse.create("badge2", "badge3")));

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