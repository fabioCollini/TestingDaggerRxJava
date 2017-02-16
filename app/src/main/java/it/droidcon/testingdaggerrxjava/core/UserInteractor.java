package it.droidcon.testingdaggerrxjava.core;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import it.droidcon.testingdaggerrxjava.core.gson.BadgeResponse;
import it.droidcon.testingdaggerrxjava.core.gson.StackOverflowService;
import it.droidcon.testingdaggerrxjava.core.gson.User;
import it.droidcon.testingdaggerrxjava.core.gson.UserResponse;
import java.util.List;

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
        .toList();
  }

  private Single<UserStats> loadUserStats(User user) {
    return service.getBadges(user.id())
        .subscribeOn(Schedulers.io())
        .map(BadgeResponse::items)
        .map(badges -> UserStats.create(user, badges));
  }
}