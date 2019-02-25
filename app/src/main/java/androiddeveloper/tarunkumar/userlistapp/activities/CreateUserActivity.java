package androiddeveloper.tarunkumar.userlistapp.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import androiddeveloper.tarunkumar.userlistapp.R;
import androiddeveloper.tarunkumar.userlistapp.databinding.CreateUserActivityBinding;
import androiddeveloper.tarunkumar.userlistapp.model.CreateUser;
import androiddeveloper.tarunkumar.userlistapp.restapi.RestApiClient;
import androiddeveloper.tarunkumar.userlistapp.restapi.UserApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateUserActivity extends AppCompatActivity {

    CreateUserActivityBinding createUserActivityBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createUserActivityBinding = DataBindingUtil.setContentView(this, R.layout.user_create);

        String user = createUserActivityBinding.enterUser.getText().toString();

        String role = createUserActivityBinding.enterRole.getText().toString();

        createUserActivityBinding.submitDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserApi userApi = RestApiClient.getRestApiClient().create(UserApi.class);
                userApi.createUser(user, role).enqueue(new Callback<CreateUser>() {
                    @Override
                    public void onResponse(@NonNull Call<CreateUser> call, @NonNull Response<CreateUser> response) {
                        if(response.isSuccessful()){
                            Log.i("***123", response.body() + "");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CreateUser> call, @NonNull Throwable t) {

                    }
                });
            }
        });
    }
}
