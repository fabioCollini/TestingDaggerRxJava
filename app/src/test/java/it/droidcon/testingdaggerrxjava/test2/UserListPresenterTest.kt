package it.droidcon.testingdaggerrxjava.test2

import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Observable
import io.reactivex.Single
import it.cosenonjaviste.daggermock.DaggerMockRule
import it.cosenonjaviste.daggermock.InjectFromComponent
import it.droidcon.testingdaggerrxjava.TrampolineSchedulerRule
import it.droidcon.testingdaggerrxjava.core.UserInteractor
import it.droidcon.testingdaggerrxjava.core.UserStats
import it.droidcon.testingdaggerrxjava.dagger.ApplicationComponent
import it.droidcon.testingdaggerrxjava.dagger.UserInteractorModule
import it.droidcon.testingdaggerrxjava.userlist.UserListActivity
import it.droidcon.testingdaggerrxjava.userlist.UserListPresenter
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

class UserListPresenterTest {

    @get:Rule var daggerMockRule = DaggerMockRule(ApplicationComponent::class.java, UserInteractorModule())

    @get:Rule var schedulerRule = TrampolineSchedulerRule()

    val userInteractor: UserInteractor = mock()

    val activity: UserListActivity = mock()

    @InjectFromComponent(UserListActivity::class) lateinit var presenter: UserListPresenter

    @Test
    @Throws(Exception::class)
    fun shouldLoadUsers() {
        `when`<Single<List<UserStats>>>(userInteractor.loadUsers()).thenReturn(
                Observable.fromArray(
                        UserStats.create(1, 50, "user1", "badge1"),
                        UserStats.create(2, 30, "user2", "badge2", "badge3")
                ).toList())

        presenter.reloadUserList()

        verify(activity).updateText(
                "50 user1 - badge1\n\n30 user2 - badge2, badge3")
    }
}