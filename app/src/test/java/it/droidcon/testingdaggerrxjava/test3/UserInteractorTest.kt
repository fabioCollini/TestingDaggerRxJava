package it.droidcon.testingdaggerrxjava.test3

import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Observable
import io.reactivex.Single
import it.droidcon.testingdaggerrxjava.core.UserInteractor
import it.droidcon.testingdaggerrxjava.core.UserStats
import it.droidcon.testingdaggerrxjava.core.gson.Badge
import it.droidcon.testingdaggerrxjava.core.gson.StackOverflowService
import it.droidcon.testingdaggerrxjava.core.gson.User
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit

class UserInteractorTest {
    @get:Rule var mockitoRule = MockitoJUnit.rule()

    val stackOverflowService: StackOverflowService = mock()

    @InjectMocks lateinit var userInteractor: UserInteractor

    @Test fun shouldLoadUsers() {
        `when`(stackOverflowService.getTopUsers()).thenReturn(
                Observable.fromArray(
                        User.create(1, 50, "user1"),
                        User.create(2, 30, "user2")
                ).toList())

        `when`(stackOverflowService.getBadges(1)).thenReturn(
                Single.just(arrayOf("badge1").map { Badge(it) })
                //.doOnSuccess(l->Thread.sleep(100))
        )
        `when`(stackOverflowService.getBadges(2)).thenReturn(
                Single.just(arrayOf("badge2", "badge3").map { Badge(it) })
        )

        val users = userInteractor.loadUsers().blockingGet()
        assertThat(users).containsExactly(
                UserStats(1, 50, "user1", listOf("badge1")),
                UserStats(2, 30, "user2", listOf("badge2", "badge3")))
    }
}