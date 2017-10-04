package it.droidcon.testingdaggerrxjava.test2

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import it.droidcon.testingdaggerrxjava.R
import it.droidcon.testingdaggerrxjava.appFromInstrumentation
import it.droidcon.testingdaggerrxjava.core.UserInteractor
import it.droidcon.testingdaggerrxjava.core.UserStats
import it.droidcon.testingdaggerrxjava.userlist.UserListActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

class UserListActivityTest {
    @get:Rule val rule = ActivityTestRule(UserListActivity::class.java, false, false)

    @Inject lateinit var userInteractor: UserInteractor

    @Before fun setUp() {
        val component = DaggerTestApplicationComponent.create()
        appFromInstrumentation.component = component
        component.inject(this)
    }

    @Test fun shouldDisplayUsers() {
        whenever(userInteractor.loadUsers()).thenReturn(
                Single.just(listOf(
                        UserStats(1, 50, "user1", listOf("badge1")),
                        UserStats(2, 30, "user2", listOf("badge2", "badge3"))
                )))

        rule.launchActivity(null)

        onView(withId(R.id.text)).check(matches(withText(
                "50 user1 - badge1\n\n30 user2 - badge2, badge3")))
    }
}