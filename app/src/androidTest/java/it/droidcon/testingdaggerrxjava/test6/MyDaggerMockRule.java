package it.droidcon.testingdaggerrxjava.test6;

import it.cosenonjaviste.daggermock.DaggerMockRule;
import it.droidcon.testingdaggerrxjava.dagger.ApplicationComponent;
import it.droidcon.testingdaggerrxjava.dagger.UserInteractorModule;

import static it.droidcon.testingdaggerrxjava.TestUtils.getAppFromInstrumentation;

public class MyDaggerMockRule extends DaggerMockRule<ApplicationComponent> {
  public MyDaggerMockRule() {
    super(
        ApplicationComponent.class,
        new UserInteractorModule()
    );
    set(component ->
        getAppFromInstrumentation().setComponent(component));
  }
}
