package it.droidcon.testingdaggerrxjava.test3

import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Observable
import io.reactivex.Single
import it.cosenonjaviste.daggermock.DaggerMockRule
import it.cosenonjaviste.daggermock.InjectFromComponent
import it.droidcon.testingdaggerrxjava.AsyncTaskSchedulerRule
import it.droidcon.testingdaggerrxjava.TestApplicationComponent
import it.droidcon.testingdaggerrxjava.TrampolineSchedulerRule
import it.droidcon.testingdaggerrxjava.UserInteractorModule
import it.droidcon.testingdaggerrxjava.core.UserInteractor
import it.droidcon.testingdaggerrxjava.core.UserStats
import it.droidcon.testingdaggerrxjava.core.gson.Badge
import it.droidcon.testingdaggerrxjava.core.gson.StackOverflowService
import it.droidcon.testingdaggerrxjava.core.gson.User
import it.droidcon.testingdaggerrxjava.dagger.StackOverflowServiceModule
import it.droidcon.testingdaggerrxjava.userlist.UserListActivity
import it.droidcon.testingdaggerrxjava.userlist.UserListPresenter
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`

class UserInteractorDaggerMockTest {

    @get:Rule val asyncTaskRule = AsyncTaskSchedulerRule()

    @get:Rule val daggerMockRule = DaggerMockRule(TestApplicationComponent::class.java, UserInteractorModule(), StackOverflowServiceModule())
            .providesMock(UserListActivity::class.java)

    @get:Rule val schedulerRule = TrampolineSchedulerRule()

    val stackOverflowService: StackOverflowService = mock()

    @InjectFromComponent(UserListActivity::class, UserListPresenter::class)
    lateinit var userInteractor: UserInteractor

    @Test fun shouldLoadUsers() {
        asyncTaskRule
        `when`(stackOverflowService.getTopUsers()).thenReturn(Observable.fromArray(
                User(1, 50, "user1"),
                User(2, 30, "user2")
        ).toList())

        `when`(stackOverflowService.getBadges(1)).thenReturn(
                Single.just(listOf(Badge("badge1"))))
        `when`(stackOverflowService.getBadges(2)).thenReturn(
                Single.just(arrayOf("badge2", "badge3").map { Badge(it) }))

        userInteractor.loadUsers()
                .test()
                .assertNoErrors()
                .assertValue(listOf(
                        UserStats(1, 50, "user1", listOf("badge1")),
                        UserStats(2, 30, "user2", listOf("badge2", "badge3"))
                ))

        val l = userInteractor.loadUsers()
                .test()
                .assertNoErrors()
                .values()

        assertThat(l[0]).containsExactly(
                UserStats(1, 50, "user1", listOf("badge1")),
                UserStats(2, 30, "user2", listOf("badge2", "badge3"))
        )
    }
}