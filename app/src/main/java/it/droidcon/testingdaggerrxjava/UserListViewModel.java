package it.droidcon.testingdaggerrxjava;

import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.os.Bundle;
import android.support.annotation.NonNull;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import it.codingjam.lifecyclebinder.BindEvent;
import it.droidcon.testingdaggerrxjava.core.UserInteractor;
import it.droidcon.testingdaggerrxjava.core.UserStats;
import javax.inject.Inject;

import static it.codingjam.lifecyclebinder.LifeCycleEvent.CREATE;
import static it.droidcon.testingdaggerrxjava.core.StringUtils.join;

public class UserListViewModel {

    @Inject UserInteractor userInteractor;

    public final UserListModel model = new UserListModel();

    public final ObservableBoolean loading = new ObservableBoolean();

    @Inject public UserListViewModel() {
    }

    @BindEvent(CREATE) void onCreate(Bundle state, Intent intent, Bundle args) {
        if (state == null) {
            loading.set(true);
            userInteractor
                    .loadUsers()
                    .flattenAsObservable(l -> l)
                    .map(this::userToString)
                    .toList()
                    .map(l -> join(l, "\n\n"))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally(() -> loading.set(false))
                    .subscribe(
                            model.userList::set,
                            Throwable::printStackTrace
                    );
        }
    }

    @NonNull private String userToString(UserStats user) {
        return user.reputation() + " " + user.name() + "\n" + join(user.badges(), ", ");
    }
}
