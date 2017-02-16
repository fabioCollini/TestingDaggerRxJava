package it.droidcon.testingdaggerrxjava.core;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import it.droidcon.testingdaggerrxjava.core.gson.Badge;
import it.droidcon.testingdaggerrxjava.core.gson.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static it.droidcon.testingdaggerrxjava.core.StringUtils.join;

@AutoValue
public abstract class UserStats {

    public static UserStats create(User user, List<Badge> badges) {
        List<String> names = new ArrayList<>();
        for (Badge badge : badges) {
            names.add(badge.name());
        }
        return new AutoValue_UserStats(user, names);
    }

    public static UserStats create(int id, int reputation, String name, String... badges) {
        return new AutoValue_UserStats(User.create(id, reputation, name), Arrays.asList(badges));
    }

    public static TypeAdapter<UserStats> typeAdapter(Gson gson) {
        return new AutoValue_UserStats.GsonTypeAdapter(gson);
    }

    public abstract User user();

    public abstract List<String> badges();

    public int id() {
        return user().id();
    }

    public int reputation() {
        return user().reputation();
    }

    public String name() {
        return user().name();
    }

    @Override public String toString() {
        return reputation() + " " + name() + "\n" + join(badges(), ", ");
    }
}
