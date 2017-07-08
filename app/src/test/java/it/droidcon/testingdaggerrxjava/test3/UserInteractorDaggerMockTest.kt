package it.droidcon.testingdaggerrxjava.test3

import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Observable
import io.reactivex.Single
import it.cosenonjaviste.daggermock.DaggerMockRule
import it.cosenonjaviste.daggermock.InjectFromComponent
import it.droidcon.testingdaggerrxjava.TrampolineSchedulerRule
import it.droidcon.testingdaggerrxjava.core.UserInteractor
import it.droidcon.testingdaggerrxjava.core.UserStats
import it.droidcon.testingdaggerrxjava.core.gson.Badge
import it.droidcon.testingdaggerrxjava.core.gson.StackOverflowService
import it.droidcon.testingdaggerrxjava.core.gson.User
import it.droidcon.testingdaggerrxjava.dagger.ApplicationComponent
import it.droidcon.testingdaggerrxjava.dagger.StackOverflowServiceModule
import it.droidcon.testingdaggerrxjava.dagger.UserInteractorModule
import it.droidcon.testingdaggerrxjava.userlist.UserListActivity
import it.droidcon.testingdaggerrxjava.userlist.UserListPresenter
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import java.util.*

class UserInteractorDaggerMockTest {
    @get:Rule var daggerMockRule = DaggerMockRule(ApplicationComponent::class.java, UserInteractorModule(), StackOverflowServiceModule())
            .providesMock(UserListActivity::class.java)

    @get:Rule var schedulerRule = TrampolineSchedulerRule()

    val stackOverflowService: StackOverflowService = mock()

    @InjectFromComponent(UserListActivity::class, UserListPresenter::class)
    lateinit var userInteractor: UserInteractor

    @Test
    @Throws(Exception::class)
    fun shouldLoadUsers() {
        `when`(stackOverflowService.getTopUsers()).thenReturn(Observable.fromArray(
                User.create(1, 50, "user1"),
                User.create(2, 30, "user2")
        ).toList())

        `when`(stackOverflowService.getBadges(1)).thenReturn(
                Single.just(listOf(Badge("badge1"))))
        `when`(stackOverflowService.getBadges(2)).thenReturn(
                Single.just(arrayOf("badge2", "badge3").map { Badge(it) }))

        userInteractor.loadUsers()
                .test()
                .assertNoErrors()
                .assertValue(Arrays.asList(
                        UserStats.create(1, 50, "user1", "badge1"),
                        UserStats.create(2, 30, "user2", "badge2", "badge3")
                ))

        val l = userInteractor.loadUsers()
                .test()
                .assertNoErrors()
                .values()

        assertThat(l[0]).containsExactly(
                UserStats.create(1, 50, "user1", "badge1"),
                UserStats.create(2, 30, "user2", "badge2", "badge3")
        )
    }
}