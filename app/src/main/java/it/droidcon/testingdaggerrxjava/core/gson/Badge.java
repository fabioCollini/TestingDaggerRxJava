package it.droidcon.testingdaggerrxjava.core.gson;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.util.ArrayList;
import java.util.List;

@AutoValue
public abstract class Badge {

    public static Badge create(String name) {
        return new AutoValue_Badge(name);
    }

    public static List<Badge> createList(String... names) {
        ArrayList<Badge> ret = new ArrayList<>();
        for (String name : names) {
            ret.add(Badge.create(name));
        }
        return ret;
    }

    public static TypeAdapter<Badge> typeAdapter(Gson gson) {
        return new AutoValue_Badge.GsonTypeAdapter(gson);
    }

    public abstract String name();

    @Override public String toString() {
        return name();
    }
}
