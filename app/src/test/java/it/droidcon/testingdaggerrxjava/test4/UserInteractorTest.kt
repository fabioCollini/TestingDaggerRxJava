package it.droidcon.testingdaggerrxjava.test4

import assertk.assert
import assertk.assertions.containsExactly
import com.nhaarman.mockito_kotlin.mock
import it.droidcon.testingdaggerrxjava.TestSchedulerRule
import it.droidcon.testingdaggerrxjava.after
import it.droidcon.testingdaggerrxjava.core.UserInteractor
import it.droidcon.testingdaggerrxjava.core.UserStats
import it.droidcon.testingdaggerrxjava.core.gson.Badge
import it.droidcon.testingdaggerrxjava.core.gson.StackOverflowService
import it.droidcon.testingdaggerrxjava.core.gson.User
import it.droidcon.testingdaggerrxjava.seconds
import it.droidcon.testingdaggerrxjava.willReturnJust
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeUnit

class UserInteractorTest {
    @get:Rule
    val schedulerRule = TestSchedulerRule()

    val stackOverflowService: StackOverflowService = mock()

    val userInteractor = UserInteractor(stackOverflowService)

    @Test
    fun shouldLoadUsers() {
        stackOverflowService.getTopUsers() willReturnJust listOf(
                User(1, 50, "user1"),
                User(2, 30, "user2")
        )

        stackOverflowService.getBadges(1) willReturnJust listOf(
                Badge("badge1")
        ) after 2.seconds()

        stackOverflowService.getBadges(2) willReturnJust listOf(
                Badge("badge2"),
                Badge("badge3")
        ) after 1.seconds()


        val testObserver = userInteractor.loadUsers().test()

        schedulerRule.testScheduler
                .advanceTimeBy(2, TimeUnit.SECONDS)

        val l = testObserver.assertNoErrors().values()
        assert(l[0]).containsExactly(
                UserStats(1, 50, "user1", listOf("badge1")),
                UserStats(2, 30, "user2", listOf("badge2", "badge3"))
        )
    }
}