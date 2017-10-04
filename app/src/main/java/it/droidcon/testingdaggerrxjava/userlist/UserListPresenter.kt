package it.droidcon.testingdaggerrxjava.userlist

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import it.droidcon.testingdaggerrxjava.core.UserInteractor

class UserListPresenter(
    private val userInteractor: UserInteractor,
    private val activity: UserListActivity
) {

  fun reloadUserList() {
    userInteractor
        .loadUsers()
        .map { l ->
          l.map { it.toString() }
              .reduce { s1, s2 -> "$s1\n\n$s2" }
        }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            { activity.updateText(it) },
            { activity.showError(it) }
        )
  }
}
