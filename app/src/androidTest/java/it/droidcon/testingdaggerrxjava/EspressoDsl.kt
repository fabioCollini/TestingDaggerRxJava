package it.droidcon.testingdaggerrxjava

import android.support.test.espresso.Espresso
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers

infix fun Int.hasText(text: String): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(this)).check(ViewAssertions.matches(ViewMatchers.withText(text)))