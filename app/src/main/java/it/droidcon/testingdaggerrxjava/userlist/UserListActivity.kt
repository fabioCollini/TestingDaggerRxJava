package it.droidcon.testingdaggerrxjava.userlist

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.Snackbar.LENGTH_LONG
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import it.droidcon.testingdaggerrxjava.R
import it.droidcon.testingdaggerrxjava.component
import javax.inject.Inject

class UserListActivity : AppCompatActivity() {

    @Inject lateinit var presenter: UserListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.userListComponent(UserListModule(this)).inject(this)
        setContentView(R.layout.activity_main)
        presenter.reloadUserList()
    }

    fun updateText(s: String) {
        (findViewById(R.id.text) as TextView).text = s
    }

    fun showError(t: Throwable) =
            Snackbar.make(findViewById(android.R.id.content), t.message ?: "", LENGTH_LONG).show()
}
