package it.droidcon.testingdaggerrxjava.test6

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import io.reactivex.Observable
import io.reactivex.Single
import it.cosenonjaviste.daggermock.DaggerMock
import it.droidcon.testingdaggerrxjava.R
import it.droidcon.testingdaggerrxjava.TestApplicationComponent
import it.droidcon.testingdaggerrxjava.UserInteractorTestModule
import it.droidcon.testingdaggerrxjava.appFromInstrumentation
import it.droidcon.testingdaggerrxjava.core.UserInteractor
import it.droidcon.testingdaggerrxjava.core.UserStats
import it.droidcon.testingdaggerrxjava.userlist.UserListActivity
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`

class UserListActivityTest {
    @get:Rule val rule = ActivityTestRule(UserListActivity::class.java, false, false)

    @get:Rule val daggerMockRule = myDaggerMockRule()

    @Mock lateinit var userInteractor: UserInteractor

    @Test fun shouldDisplayUsers() {
        `when`<Single<List<UserStats>>>(userInteractor.loadUsers()).thenReturn(
                Observable.fromArray(
                        UserStats(1, 50, "user1", listOf("badge1")),
                        UserStats(2, 30, "user2", listOf("badge2", "badge3"))
                ).toList())

        rule.launchActivity(null)

        onView(withId(R.id.text)).check(matches(withText(
                "50 user1 - badge1\n\n30 user2 - badge2, badge3")))
    }
}

fun myDaggerMockRule() = DaggerMock.rule<TestApplicationComponent>(UserInteractorTestModule()) {
    set { appFromInstrumentation.component = it }
}
