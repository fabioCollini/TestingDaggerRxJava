package it.droidcon.testingdaggerrxjava;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.VisibleForTesting;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasDispatchingActivityInjector;
import it.droidcon.testingdaggerrxjava.dagger.ApplicationComponent;
import it.droidcon.testingdaggerrxjava.dagger.DaggerApplicationComponent;
import javax.inject.Inject;

public class MyApp extends Application implements HasDispatchingActivityInjector {

    private ApplicationComponent component;

    @Inject DispatchingAndroidInjector<Activity> dispatchingActivityInjector;

    @Override public void onCreate() {
        super.onCreate();
        if (component == null) {
            component = DaggerApplicationComponent.create();
        }
        component.inject(this);
    }

    public ApplicationComponent getComponent() {
        return component;
    }

    @VisibleForTesting public void setComponent(ApplicationComponent component) {
        this.component = component;
    }

    @Override public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingActivityInjector;
    }
}
