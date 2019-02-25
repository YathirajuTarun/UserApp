package androiddeveloper.tarunkumar.userlistapp.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import androiddeveloper.tarunkumar.userlistapp.R;
import androiddeveloper.tarunkumar.userlistapp.UserApplication;
import androiddeveloper.tarunkumar.userlistapp.adapters.UserListAdapter;
import androiddeveloper.tarunkumar.userlistapp.databinding.UserListActivityBinding;
import androiddeveloper.tarunkumar.userlistapp.viewmodels.UserViewModel;

public class UserListActivity extends AppCompatActivity {

    private UserListAdapter userListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UserListActivityBinding userListActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        UserViewModel userViewModel = new UserViewModel(UserApplication.create(this));

        userListActivityBinding.userList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        userListAdapter = new UserListAdapter();

        userViewModel.getUserLiveData().observe(this, pagedList -> {
            userListAdapter.submitList(pagedList);
        });

        userViewModel.getNetworkState().observe(this, networkState -> {
            userListAdapter.setNetworkState(networkState);
        });

        userListActivityBinding.userList.setAdapter(userListAdapter);

        userListActivityBinding.fab.setOnClickListener(view -> {
            Intent intent = new Intent(getBaseContext(), CreateUserActivity.class);

            startActivity(intent);

        });
    }
}
