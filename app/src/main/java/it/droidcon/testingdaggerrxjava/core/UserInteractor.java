package it.droidcon.testingdaggerrxjava.core;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import it.droidcon.testingdaggerrxjava.core.gson.StackOverflowService;
import java.util.List;

public class UserInteractor {
  private StackOverflowService service;

  public UserInteractor(StackOverflowService service) {
    this.service = service;
  }

  public Single<List<UserStats>> loadUsers() {
    return service.getTopUsers()
        .flattenAsObservable(l -> l)
        .take(5)
        .concatMapEager(user ->
            service.getBadges(user.id())
                .subscribeOn(Schedulers.io())
                .map(badges -> UserStats.create(user, badges))
                .toObservable()
        )
        .toList();
  }
}