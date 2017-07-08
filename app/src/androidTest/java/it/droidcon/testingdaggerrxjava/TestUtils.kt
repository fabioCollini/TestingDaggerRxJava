package it.droidcon.testingdaggerrxjava

import android.support.test.InstrumentationRegistry

val appFromInstrumentation: MyApp
    get() = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as MyApp
