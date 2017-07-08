package it.droidcon.testingdaggerrxjava

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import it.droidcon.testingdaggerrxjava.test7.MyDaggerMockRule
import it.droidcon.testingdaggerrxjava.userlist.UserListActivity
import it.droidcon.testingdaggerrxjava.userlist.UserListPresenter
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify

class MockPresenterTest {

    @get:Rule val rule = ActivityTestRule(UserListActivity::class.java, false, false)

    @get:Rule val daggerMockRule = MyDaggerMockRule()

    @Mock lateinit var presenter: UserListPresenter

    @Test
    fun testOnCreate() {
        rule.launchActivity(null)

        onView(withId(R.id.text)).check(matches(withText("")))

        verify(presenter).reloadUserList()
    }
}