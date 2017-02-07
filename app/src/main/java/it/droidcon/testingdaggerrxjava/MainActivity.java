package it.droidcon.testingdaggerrxjava;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import it.codingjam.lifecyclebinder.LifeCycleBinder;
import it.codingjam.lifecyclebinder.RetainedObjectProvider;
import it.droidcon.testingdaggerrxjava.databinding.ActivityMainBinding;
import javax.inject.Inject;
import javax.inject.Provider;

public class MainActivity extends AppCompatActivity {

    @Inject @RetainedObjectProvider("viewModel") Provider<UserListViewModel> viewModelProvider;

    UserListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ((MyApp) getApplicationContext()).getComponent().inject(this);
        LifeCycleBinder.bind(this);
        binding.setViewModel(viewModel);
    }
}
