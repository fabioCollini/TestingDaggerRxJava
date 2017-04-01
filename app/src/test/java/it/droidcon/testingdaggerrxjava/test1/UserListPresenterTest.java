package it.droidcon.testingdaggerrxjava.test1;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import io.reactivex.Observable;
import it.droidcon.testingdaggerrxjava.TrampolineSchedulerRule;
import it.droidcon.testingdaggerrxjava.core.UserInteractor;
import it.droidcon.testingdaggerrxjava.core.UserStats;
import it.droidcon.testingdaggerrxjava.userlist.UserListActivity;
import it.droidcon.testingdaggerrxjava.userlist.UserListPresenter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserListPresenterTest {

    @Rule public MockitoRule mockitoRule =
            MockitoJUnit.rule();

    @Rule public TrampolineSchedulerRule schedulerRule =
            new TrampolineSchedulerRule();

    @Mock UserInteractor userInteractor;

    @Mock UserListActivity activity;

    @InjectMocks UserListPresenter presenter;

    @Test
    public void shouldLoadUsers() {
        when(userInteractor.loadUsers()).thenReturn(
                Observable.fromArray(
                        UserStats.create(1, 50, "user1", "badge1"),
                        UserStats.create(2, 30, "user2", "badge2", "badge3")
                ).toList());

        presenter.reloadUserList();

        verify(activity, never()).showError(any());

        verify(activity).updateText(
                "50 user1 - badge1\n\n30 user2 - badge2, badge3");
    }
}