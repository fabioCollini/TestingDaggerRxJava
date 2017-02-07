package it.droidcon.testingdaggerrxjava.core.gson;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface StackOverflowService {

    @GET("/users") Single<UserResponse> getTopUsers();

    @GET("/users/{userId}/badges") Single<BadgeResponse> getBadges(@Path("userId") int userId);
}
