package it.droidcon.testingdaggerrxjava

import android.app.Application
import android.support.annotation.VisibleForTesting
import it.droidcon.testingdaggerrxjava.dagger.ApplicationComponent
import it.droidcon.testingdaggerrxjava.dagger.DaggerApplicationComponent

class MyApp : Application() {

    @set:VisibleForTesting lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerApplicationComponent.create()
    }
}
