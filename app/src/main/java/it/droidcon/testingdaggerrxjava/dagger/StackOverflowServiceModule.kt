package it.droidcon.testingdaggerrxjava.dagger

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import it.droidcon.testingdaggerrxjava.core.gson.StackOverflowService
import it.droidcon.testingdaggerrxjava.core.utils.DenvelopingConverter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module class StackOverflowServiceModule {

    @Provides @Singleton fun provideOkHttpClient(): OkHttpClient =
            OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        var request = chain.request()
                        val url = request.url().newBuilder()
                                .addQueryParameter("site", "stackoverflow")
                                .addQueryParameter("key", "fruiv4j48P0HjSJ8t7a8Gg((")
                                .build()
                        request = request.newBuilder().url(url).build()
                        chain.proceed(request)
                    }
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()

    @Provides @Singleton fun provideGson(): Gson = GsonBuilder().create()

    @Provides @Singleton fun provideStackOverflowService(okHttpClient: OkHttpClient, gson: Gson): StackOverflowService =
            Retrofit.Builder()
                    .baseUrl("http://api.stackexchange.com/2.2/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .addConverterFactory(DenvelopingConverter(gson))
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build().create(StackOverflowService::class.java)
}
