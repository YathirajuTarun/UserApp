package androiddeveloper.tarunkumar.userlistapp;

import android.app.Application;
import android.content.Context;
import androiddeveloper.tarunkumar.userlistapp.restapi.RestApiClient;
import androiddeveloper.tarunkumar.userlistapp.restapi.UserApi;

public class UserApplication extends Application {

    private UserApi userApi;

    @Override
    public void onCreate() {
        super.onCreate();
    }


    private static UserApplication get(Context context) {
        return (UserApplication) context.getApplicationContext();
    }

    public static UserApplication create(Context context) {
        return UserApplication.get(context);
    }

    public UserApi getRestApi() {
        if(userApi == null) {
            userApi = RestApiClient.getRestApiClient().create(UserApi.class);
        }
        return userApi;
    }
}
