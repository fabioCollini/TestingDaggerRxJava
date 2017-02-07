package it.droidcon.testingdaggerrxjava.core.gson;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import java.util.Arrays;
import java.util.List;

@AutoValue
public abstract class BadgeResponse {
    public static BadgeResponse createBadgeResponse(List<Badge> items) {
        return new AutoValue_BadgeResponse(items);
    }

    public static BadgeResponse createBadgeResponse(Badge... items) {
        return new AutoValue_BadgeResponse(Arrays.asList(items));
    }

    public static TypeAdapter<BadgeResponse> typeAdapter(Gson gson) {
        return new AutoValue_BadgeResponse.GsonTypeAdapter(gson);
    }

    public abstract List<Badge> items();
}
