package it.droidcon.testingdaggerrxjava.test7

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import it.droidcon.testingdaggerrxjava.AsyncTaskSchedulerRule
import it.droidcon.testingdaggerrxjava.R
import it.droidcon.testingdaggerrxjava.core.UserInteractor
import it.droidcon.testingdaggerrxjava.userlist.UserListActivity
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock

class UserListActivityTest {
    @get:Rule val rule = ActivityTestRule(UserListActivity::class.java, false, false)

    @get:Rule val asyncTaskSchedulerRule = AsyncTaskSchedulerRule()

    @get:Rule val daggerMockRule = MyDaggerMockRule()

    @Mock lateinit var userInteractor: UserInteractor

    @Test fun shouldDisplayUsers() {
        rule.launchActivity(null)

        onView(withId(R.id.text)).check(matches(withText("50 user1 - badge1\n\n30 user2 - badge2, badge3")))
    }
}