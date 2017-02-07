package it.droidcon.testingdaggerrxjava;

import android.app.Application;
import android.support.annotation.VisibleForTesting;
import it.droidcon.testingdaggerrxjava.dagger.ApplicationComponent;
import it.droidcon.testingdaggerrxjava.dagger.DaggerApplicationComponent;

public class MyApp extends Application {

    private ApplicationComponent component;

    @Override public void onCreate() {
        super.onCreate();
        if (component == null) {
            component = DaggerApplicationComponent.create();
        }
    }

    public ApplicationComponent getComponent() {
        return component;
    }

    @VisibleForTesting public void setComponent(ApplicationComponent component) {
        this.component = component;
    }
}
