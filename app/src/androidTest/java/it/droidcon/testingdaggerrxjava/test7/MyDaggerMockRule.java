package it.droidcon.testingdaggerrxjava.test7;

import io.reactivex.Observable;
import it.cosenonjaviste.daggermock.DaggerMockRule;
import it.droidcon.testingdaggerrxjava.core.UserInteractor;
import it.droidcon.testingdaggerrxjava.core.UserStats;
import it.droidcon.testingdaggerrxjava.dagger.ApplicationComponent;
import it.droidcon.testingdaggerrxjava.dagger.UserInteractorModule;

import static it.droidcon.testingdaggerrxjava.TestUtils.getAppFromInstrumentation;
import static org.mockito.Mockito.when;

public class MyDaggerMockRule
    extends DaggerMockRule<ApplicationComponent> {

  public MyDaggerMockRule() {
    super(
        ApplicationComponent.class,
        new UserInteractorModule()
    );
    set(component ->
        getAppFromInstrumentation().setComponent(component));
    providesMock(UserInteractor.class, mock ->
        when(mock.loadUsers()).thenReturn(
            Observable.fromArray(
                UserStats.create(1, 50, "user1", "badge1"),
                UserStats.create(2, 30, "user2", "badge2", "badge3")
            ).toList())
    );
  }
}
