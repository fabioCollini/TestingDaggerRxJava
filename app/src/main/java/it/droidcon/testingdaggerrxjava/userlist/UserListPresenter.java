package it.droidcon.testingdaggerrxjava.userlist;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import it.droidcon.testingdaggerrxjava.core.UserInteractor;
import it.droidcon.testingdaggerrxjava.core.UserStats;

import static it.droidcon.testingdaggerrxjava.core.StringUtils.join;

public class UserListPresenter {

    private UserInteractor userInteractor;

    private UserListActivity activity;

    public UserListPresenter(UserInteractor userInteractor, UserListActivity activity) {
        this.userInteractor = userInteractor;
        this.activity = activity;
    }

    public void reloadUserList() {
        userInteractor
                .loadUsers()
                .flattenAsObservable(l -> l)
                .map(UserStats::toString)
                .toList()
                .map(l -> join(l, "\n\n"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        activity::updateText,
                        activity::showError
                );
    }
}
