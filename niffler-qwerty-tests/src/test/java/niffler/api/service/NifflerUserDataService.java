package niffler.api.service;

import niffler.model.UserDataJson;
import niffler.model.UserJson;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface NifflerUserDataService {

    String nifflerUserDataUri = "http://127.0.0.1:8089";

    @POST("/updateUserInfo")
    Call<UserDataJson> updateUserInfo(@Body UserDataJson userData);

    @GET("/currentUser")
    Call<UserJson> currentUser(@Query("username") String username);

}
