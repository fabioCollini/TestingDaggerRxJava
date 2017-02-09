package it.droidcon.testingdaggerrxjava.core.gson;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import java.util.ArrayList;
import java.util.List;

@AutoValue
public abstract class BadgeResponse {
    public static BadgeResponse create(List<Badge> items) {
        return new AutoValue_BadgeResponse(items);
    }

    public static BadgeResponse create(String... items) {
        List<Badge> badges = new ArrayList<>(items.length);
        for (String item : items) {
            badges.add(Badge.create(item));
        }
        return new AutoValue_BadgeResponse(badges);
    }

    public static TypeAdapter<BadgeResponse> typeAdapter(Gson gson) {
        return new AutoValue_BadgeResponse.GsonTypeAdapter(gson);
    }

    public abstract List<Badge> items();
}
