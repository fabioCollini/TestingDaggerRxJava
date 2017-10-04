package it.droidcon.testingdaggerrxjava

import android.app.Activity
import android.support.test.espresso.Espresso
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule

infix fun Int.hasText(text: String): ViewInteraction =
    Espresso.onView(ViewMatchers.withId(this)).check(ViewAssertions.matches(ViewMatchers.withText(text)))

inline fun <reified T : Activity> activityRule(initialTouchMode: Boolean = false, launchActivity: Boolean = false) =
    ActivityTestRule(T::class.java, initialTouchMode, launchActivity)

fun <T : Activity> ActivityTestRule<T>.launchActivity(): T = launchActivity(null)