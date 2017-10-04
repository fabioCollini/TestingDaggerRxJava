package it.droidcon.testingdaggerrxjava.test2

import it.droidcon.testingdaggerrxjava.*
import it.droidcon.testingdaggerrxjava.core.UserInteractor
import it.droidcon.testingdaggerrxjava.core.UserStats
import it.droidcon.testingdaggerrxjava.userlist.UserListActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

class UserListActivityTest2 {
    @get:Rule
    val rule = activityRule<UserListActivity>()

    @Inject lateinit var userInteractor: UserInteractor

    @Before
    fun setUp() {
        val component = DaggerTestApplicationComponent.create()
        appFromInstrumentation.component = component
        component.inject(this)
    }

    @Test
    fun shouldDisplayUsers() {
        userInteractor.loadUsers() willReturnJust listOf(
                UserStats(1, 50, "user1", listOf("badge1")),
                UserStats(2, 30, "user2", listOf("badge2", "badge3"))
        )

        rule.launchActivity()

        R.id.text hasText
                "50 user1 - badge1\n\n30 user2 - badge2, badge3"
    }
}