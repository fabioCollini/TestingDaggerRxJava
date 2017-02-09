package it.droidcon.testingdaggerrxjava;

import android.support.test.InstrumentationRegistry;

public class TestUtils {
    public static MyApp getAppFromInstrumentation() {
        return (MyApp) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
    }
}
