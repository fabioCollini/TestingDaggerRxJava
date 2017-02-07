package it.droidcon.testingdaggerrxjava.test6;

import android.support.test.InstrumentationRegistry;
import it.cosenonjaviste.daggermock.DaggerMockRule;
import it.droidcon.testingdaggerrxjava.MyApp;
import it.droidcon.testingdaggerrxjava.dagger.ApplicationComponent;
import it.droidcon.testingdaggerrxjava.dagger.UserInteractorModule;

public class MyDaggerMockRule extends DaggerMockRule<ApplicationComponent> {
    public MyDaggerMockRule() {
        super(ApplicationComponent.class, new UserInteractorModule());
        set(component -> {
            MyApp app = (MyApp) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
            app.setComponent(component);
        });
    }
}
