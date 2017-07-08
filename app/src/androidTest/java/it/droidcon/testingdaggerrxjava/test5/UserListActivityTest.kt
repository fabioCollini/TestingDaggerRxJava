package it.droidcon.testingdaggerrxjava.test5

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Observable
import it.cosenonjaviste.daggermock.DaggerMockRule
import it.droidcon.testingdaggerrxjava.AsyncTaskSchedulerRule
import it.droidcon.testingdaggerrxjava.R
import it.droidcon.testingdaggerrxjava.appFromInstrumentation
import it.droidcon.testingdaggerrxjava.core.UserInteractor
import it.droidcon.testingdaggerrxjava.core.UserStats
import it.droidcon.testingdaggerrxjava.dagger.ApplicationComponent
import it.droidcon.testingdaggerrxjava.dagger.UserInteractorModule
import it.droidcon.testingdaggerrxjava.userlist.UserListActivity
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`

class UserListActivityTest {
    @get:Rule var rule = ActivityTestRule(UserListActivity::class.java, false, false)

    @get:Rule var asyncTaskSchedulerRule = AsyncTaskSchedulerRule()

    @get:Rule var daggerMockRule = DaggerMockRule(
            ApplicationComponent::class.java,
            UserInteractorModule()
    ).set { component -> appFromInstrumentation.component = component }

    val userInteractor: UserInteractor = mock()

    @Test fun shouldDisplayUsers() {
        `when`(userInteractor.loadUsers()).thenReturn(
                Observable.fromArray(
                        UserStats.create(1, 50, "user1", "badge1"),
                        UserStats.create(2, 30, "user2", "badge2", "badge3")
                ).toList())

        rule.launchActivity(null)

        onView(withId(R.id.text)).check(matches(withText(
                "50 user1 - badge1\n\n30 user2 - badge2, badge3")))
    }
}