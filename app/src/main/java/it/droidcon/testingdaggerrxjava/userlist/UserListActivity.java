package it.droidcon.testingdaggerrxjava.userlist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import it.droidcon.testingdaggerrxjava.MyApp;
import it.droidcon.testingdaggerrxjava.R;
import javax.inject.Inject;

public class UserListActivity extends AppCompatActivity {

    @Inject UserListPresenter presenter;

    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MyApp) getApplicationContext()).getComponent()
                .userListComponent(new UserListModule(this)).inject(this);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.text);
        presenter.reloadUserList();
    }

    public void updateText(String s) {
        text.setText(s);
    }

    public void showError(Throwable throwable) {

    }
}