package it.droidcon.testingdaggerrxjava.test3

import com.nhaarman.mockito_kotlin.mock
import it.droidcon.testingdaggerrxjava.core.UserInteractor
import it.droidcon.testingdaggerrxjava.core.UserStats
import it.droidcon.testingdaggerrxjava.core.gson.Badge
import it.droidcon.testingdaggerrxjava.core.gson.StackOverflowService
import it.droidcon.testingdaggerrxjava.core.gson.User
import it.droidcon.testingdaggerrxjava.willReturnJust
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Test

class UserInteractorTest {

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
    )
    stackOverflowService.getBadges(2) willReturnJust listOf(
        Badge("badge2"),
        Badge("badge3")
    )
//    `when`(stackOverflowService.getBadges(1)).thenReturn(
//        Single.just(listOf(Badge("badge1")))
////            .doOnSuccess { Thread.sleep(100) }
//    )

    val users = userInteractor.loadUsers().blockingGet()

    assertThat(users).containsExactly(
        UserStats(1, 50, "user1", listOf("badge1")),
        UserStats(2, 30, "user2", listOf("badge2", "badge3")))
  }
}