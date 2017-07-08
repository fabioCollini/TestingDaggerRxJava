package it.droidcon.testingdaggerrxjava.test6

import it.cosenonjaviste.daggermock.DaggerMockRule
import it.droidcon.testingdaggerrxjava.appFromInstrumentation
import it.droidcon.testingdaggerrxjava.dagger.ApplicationComponent
import it.droidcon.testingdaggerrxjava.dagger.UserInteractorModule

class MyDaggerMockRule : DaggerMockRule<ApplicationComponent>(ApplicationComponent::class.java, UserInteractorModule()) {
    init {
        set { component -> appFromInstrumentation.component = component }
    }
}
