package it.droidcon.testingdaggerrxjava.core;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import it.droidcon.testingdaggerrxjava.core.gson.BadgeResponse;
import it.droidcon.testingdaggerrxjava.core.gson.StackOverflowService;
import it.droidcon.testingdaggerrxjava.core.gson.User;
import it.droidcon.testingdaggerrxjava.core.gson.UserResponse;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class UserInteractor {
    private StackOverflowService service;

    public UserInteractor(StackOverflowService service) {
        this.service = service;
    }

    public Single<List<UserStats>> loadUsers() {
        return service.getTopUsers()
                .flattenAsObservable(UserResponse::items)
                .take(5)
                .concatMapEager(user -> loadUserStats(user).toObservable())
                .toList()
                .retry(1)
                .timeout(20, TimeUnit.SECONDS);
    }

    private Single<UserStats> loadUserStats(User user) {
        return service.getBadges(user.id())
                .subscribeOn(Schedulers.io())
                .map(BadgeResponse::items)
                .map(badges -> UserStats.create(user, badges));
    }
}