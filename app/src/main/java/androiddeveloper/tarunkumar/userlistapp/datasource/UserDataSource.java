package androiddeveloper.tarunkumar.userlistapp.datasource;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import java.util.Objects;
import androiddeveloper.tarunkumar.userlistapp.UserApplication;
import androiddeveloper.tarunkumar.userlistapp.model.GetUsers;
import androiddeveloper.tarunkumar.userlistapp.model.UserData;
import androiddeveloper.tarunkumar.userlistapp.utils.NetworkState;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDataSource extends PageKeyedDataSource<Long, UserData> {

    private UserApplication appController;

    private MutableLiveData<NetworkState> networkState;
    private MutableLiveData<NetworkState> initialLoading;

    UserDataSource(UserApplication appController) {
        this.appController = appController;

        networkState = new MutableLiveData<>();
        initialLoading = new MutableLiveData<>();
    }


    public MutableLiveData getNetworkState() {
        return networkState;
    }

    @Override
    public void loadInitial(@NonNull PageKeyedDataSource.LoadInitialParams<Long> params,
                            @NonNull final PageKeyedDataSource.LoadInitialCallback<Long, UserData> callback) {

        initialLoading.postValue(NetworkState.LOADING);
        networkState.postValue(NetworkState.LOADING);

        appController.getRestApi().fetchUsers(1)
                .enqueue(new Callback<GetUsers>() {
                    @Override
                    public void onResponse(@NonNull Call<GetUsers> call, @NonNull Response<GetUsers> response) {
                        if(response.isSuccessful()) {

                            callback.onResult(Objects.requireNonNull(response.body()).getUserDataList(), null, 2l);
                            initialLoading.postValue(NetworkState.LOADED);
                            networkState.postValue(NetworkState.LOADED);

                        } else {
                            initialLoading.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                            networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<GetUsers> call, @NonNull Throwable error) {
                        String errorMessage = error.getMessage();
                        networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull PageKeyedDataSource.LoadParams<Long> params,
                           @NonNull PageKeyedDataSource.LoadCallback<Long, UserData> callback) {

    }

    @Override
    public void loadAfter(@NonNull final PageKeyedDataSource.LoadParams<Long> params,
                          @NonNull final PageKeyedDataSource.LoadCallback<Long, UserData> callback) {

        networkState.postValue(NetworkState.LOADING);

        appController.getRestApi().fetchUsers(params.key).enqueue(new Callback<GetUsers>() {
            @Override
            public void onResponse(@NonNull Call<GetUsers> call, @NonNull Response<GetUsers> response) {
                if(response.isSuccessful()) {

                    callback.onResult(Objects.requireNonNull(response.body()).getUserDataList(), params.key + 1);

                    networkState.postValue(NetworkState.LOADED);

                } else networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
            }

            @Override
            public void onFailure(@NonNull Call<GetUsers> call, @NonNull Throwable error) {
                String errorMessage = error.getMessage();

                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
            }
        });
    }
}