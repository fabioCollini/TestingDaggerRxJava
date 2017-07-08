package it.droidcon.testingdaggerrxjava.test1

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Observable
import it.droidcon.testingdaggerrxjava.TrampolineSchedulerRule
import it.droidcon.testingdaggerrxjava.core.UserInteractor
import it.droidcon.testingdaggerrxjava.core.UserStats
import it.droidcon.testingdaggerrxjava.userlist.UserListActivity
import it.droidcon.testingdaggerrxjava.userlist.UserListPresenter
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit

class UserListPresenterTest {

    @get:Rule val mockitoRule = MockitoJUnit.rule()

    @get:Rule val schedulerRule = TrampolineSchedulerRule()

    @Mock lateinit var userInteractor: UserInteractor

    @Mock lateinit var activity: UserListActivity

    @InjectMocks lateinit var presenter: UserListPresenter

    @Test fun shouldLoadUsers() {
        `when`(userInteractor.loadUsers()).thenReturn(
                Observable.fromArray(
                        UserStats(1, 50, "user1", listOf("badge1")),
                        UserStats(2, 30, "user2", listOf("badge2", "badge3"))
                ).toList())

        presenter.reloadUserList()

        verify(activity, never()).showError(any())

        verify(activity).updateText(
                "50 user1 - badge1\n\n30 user2 - badge2, badge3")
    }
}