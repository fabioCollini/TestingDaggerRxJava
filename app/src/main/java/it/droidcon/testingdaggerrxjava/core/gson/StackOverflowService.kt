package it.droidcon.testingdaggerrxjava.core.gson

import io.reactivex.Single
import it.droidcon.testingdaggerrxjava.core.utils.EnvelopePayload
import retrofit2.http.GET
import retrofit2.http.Path

interface StackOverflowService {

    @EnvelopePayload("items")
    @GET("/users")
    fun getTopUsers(): Single<List<User>>

    @EnvelopePayload("items")
    @GET("/users/{userId}/badges")
    fun getBadges(@Path("userId") userId: Int): Single<List<Badge>>
}
