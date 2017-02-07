package it.droidcon.testingdaggerrxjava;

import dagger.Provides;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class DaggerMockExample {
    public static class MyModule {
        @Provides public String provideString() {
            return "myString";
        }

        @Provides public int provideInt() {
            return 123;
        }
    }

    @Test
    public void myTest() throws Exception {
        MyModule myModule = new MyModule();
        Map<Class, Object> testFields = collectTestFields();
        MyModule subclass = createSubclass(myModule, testFields);
        System.out.println(subclass.provideString());
        System.out.println(subclass.provideInt());
    }

    private static <T> T createSubclass(final T module, final Map<Class, Object> testFields) {
        return (T) Mockito.mock(module.getClass(), new Answer() {
            @Override public Object answer(InvocationOnMock invocation) throws Throwable {
                Method method = invocation.getMethod();
                Object obj = testFields.get(method.getReturnType());
                if (obj != null) {
                    return obj;
                } else {
                    return method.invoke(module, invocation.getArguments());
                }
            }
        });
    }

    private static Map<Class, Object> collectTestFields() {
        HashMap<Class, Object> ret = new HashMap<>();
        ret.put(String.class, "test");
        //ret.put(Integer.TYPE, 456);
        return ret;
    }
}
