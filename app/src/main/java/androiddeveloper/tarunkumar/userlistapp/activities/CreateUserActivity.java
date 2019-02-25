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

        createUserActivityBinding.validationText.setVisibility(View.INVISIBLE);

        createUserActivityBinding.createButton.setOnClickListener(view -> {

            String user = createUserActivityBinding.enterUserName.getText().toString();

            String role = createUserActivityBinding.enterUserRole.getText().toString();

            if( !user.isEmpty() && !role.isEmpty()){
                UserApi userApi = RestApiClient.getRestApiClient().create(UserApi.class);
                userApi.createUser(user, role).enqueue(new Callback<CreateUser>() {
                    @Override
                    public void onResponse(@NonNull Call<CreateUser> call, @NonNull Response<CreateUser> response) {
                        if(response.isSuccessful()){

                            CreateUser createUser = response.body();
                            if(createUser!=null){
                                createUserActivityBinding.validationText.setVisibility(View.VISIBLE);
                                createUserActivityBinding.validationText.setTextColor(getResources().getColor(R.color.green));
                                createUserActivityBinding.validationText.setText(String.format("User \" %s \" successfully created at %s", createUser.getName(), createUser.getCreatedAt()));
                                createUserActivityBinding.enterUserName.setText("");
                                createUserActivityBinding.enterUserRole.setText("");
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CreateUser> call, @NonNull Throwable t) {

                    }
                });
            }else{
                createUserActivityBinding.validationText.setVisibility(View.VISIBLE);
                createUserActivityBinding.validationText.setTextColor(getResources().getColor(R.color.red));
                createUserActivityBinding.validationText.setText(getResources().getString(R.string.create_user_validation_text));
            }


        });
    }
}
