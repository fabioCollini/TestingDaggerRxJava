package it.droidcon.testingdaggerrxjava.core.gson;

import io.reactivex.Single;
import it.droidcon.testingdaggerrxjava.core.utils.EnvelopePayload;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface StackOverflowService {

  @EnvelopePayload("items")
  @GET("/users") Single<List<User>> getTopUsers();

  @EnvelopePayload("items")
  @GET("/users/{userId}/badges") Single<List<Badge>> getBadges(@Path("userId") int userId);
}
