package it.droidcon.testingdaggerrxjava.test2;

import io.reactivex.Observable;
import it.cosenonjaviste.daggermock.DaggerMockRule;
import it.cosenonjaviste.daggermock.InjectFromComponent;
import it.droidcon.testingdaggerrxjava.TrampolineSchedulerRule;
import it.droidcon.testingdaggerrxjava.core.UserInteractor;
import it.droidcon.testingdaggerrxjava.core.UserStats;
import it.droidcon.testingdaggerrxjava.dagger.ApplicationComponent;
import it.droidcon.testingdaggerrxjava.dagger.UserInteractorModule;
import it.droidcon.testingdaggerrxjava.userlist.UserListActivity;
import it.droidcon.testingdaggerrxjava.userlist.UserListPresenter;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserListPresenterTest {

    @Rule public DaggerMockRule<ApplicationComponent> daggerMockRule =
            new DaggerMockRule<>(ApplicationComponent.class, new UserInteractorModule());

    @Rule public TrampolineSchedulerRule schedulerRule = new TrampolineSchedulerRule();

    @Mock UserInteractor userInteractor;

    @Mock UserListActivity activity;

    @InjectFromComponent(UserListActivity.class) UserListPresenter presenter;

    @Test
    public void shouldLoadUsers() throws Exception {
        when(userInteractor.loadUsers()).thenReturn(Observable.fromArray(
                UserStats.create(1, 50, "user1", "badge1"),
                UserStats.create(2, 30, "user2", "badge2", "badge3")
        ).toList());

        presenter.reloadUserList();

        verify(activity).updateText("50 user1\nbadge1\n\n30 user2\nbadge2, badge3");
    }
}