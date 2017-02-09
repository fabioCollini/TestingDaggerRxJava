package it.droidcon.testingdaggerrxjava.core;

import java.util.List;

public class StringUtils {
    public static String join(List<?> l, String separator) {
        StringBuilder b = new StringBuilder();
        for (Object o : l) {
            if (b.length() > 0) {
                b.append(separator);
            }
            b.append(o);
        }
        return b.toString();
    }
}
