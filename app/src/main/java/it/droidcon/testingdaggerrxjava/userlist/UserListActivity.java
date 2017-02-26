package it.droidcon.testingdaggerrxjava.userlist;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import dagger.android.AndroidInjection;
import it.droidcon.testingdaggerrxjava.R;
import javax.inject.Inject;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

public class UserListActivity extends AppCompatActivity {

  @Inject UserListPresenter presenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    AndroidInjection.inject(this);
    setContentView(R.layout.activity_main);
    presenter.reloadUserList();
  }

  public void updateText(String s) {
    ((TextView) findViewById(R.id.text)).setText(s);
  }

  public void showError(Throwable t) {
    Snackbar.make(findViewById(android.R.id.content),
        t.getMessage(), LENGTH_LONG).show();
  }
}
