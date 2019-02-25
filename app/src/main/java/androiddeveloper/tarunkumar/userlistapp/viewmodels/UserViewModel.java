package androiddeveloper.tarunkumar.userlistapp.viewmodels;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import androiddeveloper.tarunkumar.userlistapp.UserApplication;
import androiddeveloper.tarunkumar.userlistapp.datasource.UserDataFactory;
import androiddeveloper.tarunkumar.userlistapp.datasource.UserDataSource;
import androiddeveloper.tarunkumar.userlistapp.model.UserData;
import androiddeveloper.tarunkumar.userlistapp.utils.NetworkState;
import static android.arch.lifecycle.Transformations.switchMap;

public class UserViewModel extends ViewModel {

    private LiveData<NetworkState> networkState;
    private LiveData<PagedList<UserData>> userLiveData;
    private UserApplication appController;

    public UserViewModel(@NonNull UserApplication appController) {
        this.appController = appController;
        init();
    }

    private void init() {
        Executor executor = Executors.newFixedThreadPool(5);

        UserDataFactory userDataFactory = new UserDataFactory(appController);
        networkState = switchMap(userDataFactory.getMutableLiveData(),
                (Function<UserDataSource, LiveData<NetworkState>>) UserDataSource::getNetworkState);

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(10)
                        .setPageSize(20).build();

        userLiveData = (new LivePagedListBuilder(userDataFactory, pagedListConfig))
                .setFetchExecutor(executor)
                .build();
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public LiveData<PagedList<UserData>> getUserLiveData() {
        return userLiveData;
    }
}