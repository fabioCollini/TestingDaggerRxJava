package it.droidcon.testingdaggerrxjava.test5

import com.nhaarman.mockito_kotlin.mock
import it.cosenonjaviste.daggermock.DaggerMock
import it.droidcon.testingdaggerrxjava.*
import it.droidcon.testingdaggerrxjava.core.UserInteractor
import it.droidcon.testingdaggerrxjava.core.UserStats
import it.droidcon.testingdaggerrxjava.dagger.ApplicationComponent
import it.droidcon.testingdaggerrxjava.dagger.UserInteractorModule
import it.droidcon.testingdaggerrxjava.userlist.UserListActivity
import org.junit.Rule
import org.junit.Test

class UserListActivityTest {
  @get:Rule val rule = activityRule<UserListActivity>()

  @get:Rule
  val daggerMockRule = DaggerMock.rule<ApplicationComponent>(UserInteractorModule()) {
    set { appFromInstrumentation.component = it }
  }

  val userInteractor: UserInteractor = mock()

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