package niffler.api.service;

import niffler.model.UserDataJson;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface NifflerUserDataService {

    String nifflerUserDataUri = "http://127.0.0.1:8089";

    @POST("/updateUserInfo")
    Call<UserDataJson> updateUserInfo(@Body UserDataJson userData);

}
