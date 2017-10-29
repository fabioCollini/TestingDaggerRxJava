package it.droidcon.testingdaggerrxjava.test2

import com.nhaarman.mockito_kotlin.mock
import it.cosenonjaviste.daggermock.DaggerMock
import it.cosenonjaviste.daggermock.InjectFromComponent
import it.droidcon.testingdaggerrxjava.TestApplicationComponent
import it.droidcon.testingdaggerrxjava.TrampolineSchedulerRule
import it.droidcon.testingdaggerrxjava.UserInteractorModule
import it.droidcon.testingdaggerrxjava.core.UserInteractor
import it.droidcon.testingdaggerrxjava.core.UserStats
import it.droidcon.testingdaggerrxjava.userlist.UserListActivity
import it.droidcon.testingdaggerrxjava.userlist.UserListPresenter
import it.droidcon.testingdaggerrxjava.willReturnJust
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.verify

class UserListPresenterTest2 {

    @get:Rule val daggerMockRule = DaggerMock.rule<TestApplicationComponent>(UserInteractorModule())

    @get:Rule val schedulerRule = TrampolineSchedulerRule()

    val userInteractor: UserInteractor = mock()

    val activity: UserListActivity = mock()

    @InjectFromComponent(UserListActivity::class) lateinit var presenter: UserListPresenter

    @Test fun shouldLoadUsers() {
        userInteractor.loadUsers() willReturnJust listOf(
                UserStats(1, 50, "user1", listOf("badge1")),
                UserStats(2, 30, "user2", listOf("badge2", "badge3"))
        )

        presenter.reloadUserList()

        verify(activity).updateText(
                "50 user1 - badge1\n\n30 user2 - badge2, badge3")
    }
}