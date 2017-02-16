package it.droidcon.testingdaggerrxjava.dagger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import dagger.Module;
import dagger.Provides;
import it.droidcon.testingdaggerrxjava.core.gson.MyAdapterFactory;
import it.droidcon.testingdaggerrxjava.core.gson.StackOverflowService;
import it.droidcon.testingdaggerrxjava.core.utils.DenvelopingConverter;
import javax.inject.Singleton;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class StackOverflowServiceModule {

    @Provides @Singleton public OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    HttpUrl url = request.url().newBuilder()
                            .addQueryParameter("site", "stackoverflow")
                            .addQueryParameter("key", "fruiv4j48P0HjSJ8t7a8Gg((")
                            .build();
                    request = request.newBuilder().url(url).build();
                    return chain.proceed(request);
                });
        return builder
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }

    @Provides @Singleton public Gson provideGson() {
        return new GsonBuilder()
                .registerTypeAdapterFactory(MyAdapterFactory.create())
                .create();
    }

    @Provides @Singleton public StackOverflowService provideStackOverflowService(OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl("http://api.stackexchange.com/2.2/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .addConverterFactory(new DenvelopingConverter(gson))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(StackOverflowService.class);
    }
}
