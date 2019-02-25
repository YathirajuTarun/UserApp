package androiddeveloper.tarunkumar.userlistapp.restapi;

import androiddeveloper.tarunkumar.userlistapp.model.CreateUser;
import androiddeveloper.tarunkumar.userlistapp.model.GetUsers;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserApi {

    @GET("/api/users")
    Call<GetUsers> fetchUsers(@Query("page") long page );

    @FormUrlEncoded
    @POST("/api/users")
    Call<CreateUser> createUser(@Field("name") String name, @Field("job") String job );
}
