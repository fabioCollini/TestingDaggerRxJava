package it.droidcon.testingdaggerrxjava.test7

import it.cosenonjaviste.daggermock.DaggerMock
import it.droidcon.testingdaggerrxjava.*
import it.droidcon.testingdaggerrxjava.core.UserInteractor
import it.droidcon.testingdaggerrxjava.core.UserStats
import it.droidcon.testingdaggerrxjava.userlist.UserListActivity
import org.junit.Rule
import org.junit.Test

class UserListActivityTest {
    @get:Rule val rule = activityRule<UserListActivity>()

    @get:Rule val daggerMockRule = myDaggerMockRule()

    @Test fun shouldDisplayUsers() {
        rule.launchActivity()

        R.id.text hasText "50 user1 - badge1\n\n30 user2 - badge2, badge3"
    }
}

fun myDaggerMockRule() = DaggerMock.rule<EspressoTestApplicationComponent>(EspressoUserInteractorModule()) {
    providesMock<UserInteractor> {
        it.loadUsers() willReturnJust listOf(
                UserStats(1, 50, "user1", listOf("badge1")),
                UserStats(2, 30, "user2", listOf("badge2", "badge3"))
        )
    }
    set { appFromInstrumentation.component = it }
}
