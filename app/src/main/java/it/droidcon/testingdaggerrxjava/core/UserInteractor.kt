package it.droidcon.testingdaggerrxjava.core

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import it.droidcon.testingdaggerrxjava.core.gson.StackOverflowService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserInteractor @Inject constructor(
        private val service: StackOverflowService
) {

    fun loadUsers(): Single<List<UserStats>> =
            service.getTopUsers()
                    .flattenAsObservable { it }
                    .take(5)
                    .concatMapEager { user ->
                        service.getBadges(user.id)
                                .subscribeOn(Schedulers.io())
                                .map { badges ->
                                    UserStats(user, badges.map { it.name })
                                }
                                .toObservable()
                    }
                    .toList()
}