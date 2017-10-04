package it.droidcon.testingdaggerrxjava.test3

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Observable
import it.droidcon.testingdaggerrxjava.R
import it.droidcon.testingdaggerrxjava.appFromInstrumentation
import it.droidcon.testingdaggerrxjava.core.UserInteractor
import it.droidcon.testingdaggerrxjava.core.UserStats
import it.droidcon.testingdaggerrxjava.core.gson.StackOverflowService
import it.droidcon.testingdaggerrxjava.dagger.UserInteractorModule
import it.droidcon.testingdaggerrxjava.userlist.UserListActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import javax.inject.Inject

class UserListActivityTest {
    @get:Rule val rule = ActivityTestRule(UserListActivity::class.java, false, false)

    @Inject lateinit var userInteractor: UserInteractor

    @Before fun setUp() {
        val component = DaggerTestApplicationComponent.builder()
                .userInteractorModule(object : UserInteractorModule() {
                    override fun provideUserInteractor(
                            stackOverflowService: StackOverflowService
                    ): UserInteractor = mock()
                })
                .build()
        appFromInstrumentation.component = component
        component.inject(this)
    }

    @Test fun shouldDisplayUsers() {
        `when`(userInteractor.loadUsers()).thenReturn(
                Observable.fromArray(
                        UserStats(1, 50, "user1", listOf("badge1")),
                        UserStats(2, 30, "user2", listOf("badge2", "badge3"))
                ).toList())

        rule.launchActivity(null)

        onView(withId(R.id.text)).check(matches(withText(
                "50 user1 - badge1\n\n30 user2 - badge2, badge3")))
    }
}