package it.droidcon.testingdaggerrxjava;

import it.droidcon.testingdaggerrxjava.core.UserInteractor;
import it.droidcon.testingdaggerrxjava.core.gson.BadgeResponse;
import it.droidcon.testingdaggerrxjava.core.gson.StackOverflowService;
import it.droidcon.testingdaggerrxjava.core.gson.User;
import it.droidcon.testingdaggerrxjava.core.gson.UserResponse;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static io.reactivex.Single.just;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

public class UserServiceTest_3 {

    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @Rule public ImmediateSchedulerRule immediateSchedulerRule = new ImmediateSchedulerRule();

    @Mock StackOverflowService stackOverflowService;

    @InjectMocks UserInteractor userInteractor;

    @Test public void testSubscribe() {
        when(stackOverflowService.getTopUsers()).thenReturn(
                just(UserResponse.create(User.create(1, 10, "user 1")))
        );
        when(stackOverflowService.getBadges(anyInt())).thenReturn(
                just(BadgeResponse.create("badge"))
        );

        //List<UserStats> l = userInteractor.loadUsers().blockingGet();
        //assertThat(l).hasSize(1);

        userInteractor.loadUsers().test()
                .assertNoErrors()
                .assertValue(l -> l.size() == 1);
    }
}