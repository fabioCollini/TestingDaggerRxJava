package it.droidcon.testingdaggerrxjava.core.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * A {@link retrofit2.Converter.Factory} which removes unwanted wrapping envelopes from API
 * responses.
 */
public class DenvelopingConverter extends Converter.Factory {

    final Gson gson;

    public DenvelopingConverter(@NonNull Gson gson) {
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(
            Type type, Annotation[] annotations, Retrofit retrofit) {

        // This converter requires an annotation providing the name of the payload in the envelope;
        // if one is not supplied then return null to continue down the converter chain.
        final String payloadName = getPayloadName(annotations);
        if (payloadName == null) return null;

        final TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new Converter<ResponseBody, Object>() {
            @Override
            public Object convert(ResponseBody body) throws IOException {
                try {
                    JsonReader jsonReader = gson.newJsonReader(body.charStream());
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {
                        if (payloadName.equals(jsonReader.nextName())) {
                            return adapter.read(jsonReader);
                        } else {
                            jsonReader.skipValue();
                        }
                    }
                    return null;
                } finally {
                    body.close();
                }
            }
        };
    }

    private @Nullable String getPayloadName(Annotation[] annotations) {
        if (annotations == null) {
            return null;
        }
        for (Annotation annotation : annotations) {
            if (annotation instanceof EnvelopePayload) {
                return ((EnvelopePayload) annotation).value();
            }
        }
        return null;
    }
}