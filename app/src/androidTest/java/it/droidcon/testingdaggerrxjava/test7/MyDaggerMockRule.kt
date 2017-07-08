package it.droidcon.testingdaggerrxjava.test7

import io.reactivex.Observable
import it.cosenonjaviste.daggermock.DaggerMockRule
import it.droidcon.testingdaggerrxjava.appFromInstrumentation
import it.droidcon.testingdaggerrxjava.core.UserInteractor
import it.droidcon.testingdaggerrxjava.core.UserStats
import it.droidcon.testingdaggerrxjava.dagger.ApplicationComponent
import it.droidcon.testingdaggerrxjava.dagger.UserInteractorModule
import org.mockito.Mockito.`when`

class MyDaggerMockRule : DaggerMockRule<ApplicationComponent>(ApplicationComponent::class.java, UserInteractorModule()) {
    init {
        set { appFromInstrumentation.component = it }
        providesMock(UserInteractor::class.java) { mock ->
            `when`(mock.loadUsers()).thenReturn(
                    Observable.fromArray(
                            UserStats.create(1, 50, "user1", "badge1"),
                            UserStats.create(2, 30, "user2", "badge2", "badge3")
                    ).toList())
        }
    }
}
