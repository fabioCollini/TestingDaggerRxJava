package it.droidcon.testingdaggerrxjava.test3

import com.nhaarman.mockito_kotlin.mock
import it.cosenonjaviste.daggermock.InjectFromComponent
import it.droidcon.testingdaggerrxjava.DaggerMock
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
import it.droidcon.testingdaggerrxjava.willReturnJust
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Rule
import org.junit.Test

class UserInteractorDaggerMockTest2 {
    @get:Rule val daggerMockRule = DaggerMock.rule<ApplicationComponent>(UserInteractorModule(), StackOverflowServiceModule()) {
        providesMock<UserListActivity>()
    }

    @get:Rule val schedulerRule = TrampolineSchedulerRule()

    val stackOverflowService: StackOverflowService = mock()

    @InjectFromComponent(UserListActivity::class, UserListPresenter::class)
    lateinit var userInteractor: UserInteractor

    @Test fun shouldLoadUsers() {
        stackOverflowService.getTopUsers() willReturnJust
                listOf(User(1, 50, "user1"), User(2, 30, "user2"))

        stackOverflowService.getBadges(1) willReturnJust
                listOf(Badge("badge1"))

        stackOverflowService.getBadges(2) willReturnJust
                listOf(Badge("badge2"), Badge("badge3"))

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