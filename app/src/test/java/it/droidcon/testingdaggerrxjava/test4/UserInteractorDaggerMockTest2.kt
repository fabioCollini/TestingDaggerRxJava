package it.droidcon.testingdaggerrxjava.test4

import assertk.assert
import assertk.assertions.containsExactly
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Single
import it.cosenonjaviste.daggermock.DaggerMock
import it.cosenonjaviste.daggermock.InjectFromComponent
import it.droidcon.testingdaggerrxjava.*
import it.droidcon.testingdaggerrxjava.core.UserInteractor
import it.droidcon.testingdaggerrxjava.core.UserStats
import it.droidcon.testingdaggerrxjava.core.gson.Badge
import it.droidcon.testingdaggerrxjava.core.gson.StackOverflowService
import it.droidcon.testingdaggerrxjava.core.gson.User
import it.droidcon.testingdaggerrxjava.userlist.UserListActivity
import it.droidcon.testingdaggerrxjava.userlist.UserListPresenter
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeUnit

class UserInteractorDaggerMockTest2 {
    @get:Rule
    val daggerMockRule = DaggerMock.rule<TestApplicationComponent>(UserInteractorTestModule(), StackOverflowServiceTestModule()) {
        providesMock<UserListActivity>()
    }

    @get:Rule
    val schedulerRule = TestSchedulerRule()

    val stackOverflowService: StackOverflowService = mock()

    @InjectFromComponent(UserListActivity::class, UserListPresenter::class)
    lateinit var userInteractor: UserInteractor

    @Test
    fun shouldLoadUsers() {
        stackOverflowService.getTopUsers() willReturnJust
                listOf(User(1, 50, "user1"), User(2, 30, "user2"))

        stackOverflowService.getBadges(1) willReturn
                Single.just(listOf(Badge("badge1")))
                        .delay(2, TimeUnit.SECONDS)

        stackOverflowService.getBadges(2) willReturn
                Single.just(listOf(Badge("badge2"), Badge("badge3")))
                        .delay(1, TimeUnit.SECONDS)

        val testObserver = userInteractor.loadUsers().test()

        schedulerRule.testScheduler.advanceTimeBy(2, TimeUnit.SECONDS)

        val l = testObserver.assertNoErrors().values()
        assert(l[0]).containsExactly(
                UserStats(1, 50, "user1", listOf("badge1")),
                UserStats(2, 30, "user2", listOf("badge2", "badge3"))
        )
    }
}