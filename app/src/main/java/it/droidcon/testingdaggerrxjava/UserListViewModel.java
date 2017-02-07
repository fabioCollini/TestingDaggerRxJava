package it.droidcon.testingdaggerrxjava;

import android.content.Intent;
import android.os.Bundle;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import it.codingjam.lifecyclebinder.BindEvent;
import it.droidcon.testingdaggerrxjava.core.UserInteractor;
import javax.inject.Inject;

import static it.codingjam.lifecyclebinder.LifeCycleEvent.CREATE;

public class UserListViewModel {

    @Inject UserInteractor userInteractor;

    public final UserListModel model = new UserListModel();

    @Inject public UserListViewModel() {
    }

    @BindEvent(CREATE) void onCreate(Bundle state, Intent intent, Bundle args) {
        if (state == null) {
            userInteractor
                    .loadUsers()
                    .flattenAsObservable(l -> l)
                    .reduce("", (s, userStats) -> s + userStats + "\n\n")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            model.userList::set,
                            Throwable::printStackTrace
                    );
        }
    }
}
