package it.droidcon.testingdaggerrxjava

import com.nhaarman.mockito_kotlin.mock
import it.droidcon.testingdaggerrxjava.test7.myDaggerMockRule
import it.droidcon.testingdaggerrxjava.userlist.UserListActivity
import it.droidcon.testingdaggerrxjava.userlist.UserListPresenter
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.verify

class MockPresenterTest {

    @get:Rule val rule = activityRule<UserListActivity>()

    @get:Rule val daggerMockRule = myDaggerMockRule()

    val presenter: UserListPresenter = mock()

    @Test fun testOnCreate() {
        rule.launchActivity(null)

        R.id.text hasText ""

        verify(presenter).reloadUserList()
    }
}