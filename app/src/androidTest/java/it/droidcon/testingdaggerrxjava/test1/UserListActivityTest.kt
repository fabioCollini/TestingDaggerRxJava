package it.droidcon.testingdaggerrxjava.test1

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import io.reactivex.Observable
import io.reactivex.Single
import it.droidcon.testingdaggerrxjava.AsyncTaskSchedulerRule
import it.droidcon.testingdaggerrxjava.R
import it.droidcon.testingdaggerrxjava.appFromInstrumentation
import it.droidcon.testingdaggerrxjava.core.gson.Badge
import it.droidcon.testingdaggerrxjava.core.gson.StackOverflowService
import it.droidcon.testingdaggerrxjava.core.gson.User
import it.droidcon.testingdaggerrxjava.userlist.UserListActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import javax.inject.Inject

class UserListActivityTest {
    @get:Rule val rule = ActivityTestRule(UserListActivity::class.java, false, false)

    @get:Rule val asyncTaskSchedulerRule = AsyncTaskSchedulerRule()

    @Inject lateinit var stackOverflowService: StackOverflowService

    @Before fun setUp() {
        val applicationComponent = DaggerTestApplicationComponent.create()
        appFromInstrumentation.component = applicationComponent
        applicationComponent.inject(this)
    }

    @Test fun shouldDisplayUsers() {
        `when`(stackOverflowService.getTopUsers()).thenReturn(Observable.fromArray(
                User(1, 50, "user1"),
                User(2, 30, "user2")
        ).doOnNext { Thread.sleep(300) }.toList())

        `when`(stackOverflowService.getBadges(1)).thenReturn(
                Single.just(listOf(Badge("badge1"))))
        `when`(stackOverflowService.getBadges(2)).thenReturn(
                Single.just(listOf("badge2", "badge3").map { Badge(it) }))

        rule.launchActivity(null)

        onView(withId(R.id.text)).check(matches(withText(
                "50 user1 - badge1\n\n30 user2 - badge2, badge3")))
    }
}