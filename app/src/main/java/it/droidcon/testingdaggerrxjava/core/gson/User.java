package it.droidcon.testingdaggerrxjava.core.gson;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class User {

    public static User create(int id, int reputation, String name) {
        return new AutoValue_User(id, reputation, name);
    }

    public static TypeAdapter<User> typeAdapter(Gson gson) {
        return new AutoValue_User.GsonTypeAdapter(gson);
    }

    @SerializedName("user_id")
    public abstract int id();

    public abstract int reputation();

    @SerializedName("display_name")
    public abstract String name();
}
