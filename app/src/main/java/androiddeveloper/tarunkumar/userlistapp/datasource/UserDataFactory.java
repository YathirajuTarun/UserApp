package androiddeveloper.tarunkumar.userlistapp.datasource;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import androiddeveloper.tarunkumar.userlistapp.UserApplication;

public class UserDataFactory extends DataSource.Factory {

    private MutableLiveData<UserDataSource> mutableLiveData;
    private UserApplication appController;

    public UserDataFactory(UserApplication appController) {
        this.appController = appController;
        this.mutableLiveData = new MutableLiveData<UserDataSource>();
    }

    @Override
    public DataSource create() {
        UserDataSource userDataSource = new UserDataSource(appController);
        mutableLiveData.postValue(userDataSource);
        return userDataSource;
    }

    public MutableLiveData<UserDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}

