package niffler.api;

import niffler.model.UserDataJson;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class NifflerUserDataClient {

    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private OkHttpClient okHttpClient = new OkHttpClient
            .Builder()
            .addNetworkInterceptor(interceptor)
            .build();

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(NifflerUserDataService.nifflerUserDataUri)
            .addConverterFactory(JacksonConverterFactory.create())
            .client(okHttpClient)
            .build();

    private NifflerUserDataService nifflerUserDataService = retrofit.create(NifflerUserDataService.class);

    public UserDataJson updateUserData(UserDataJson userDataJson) throws Exception {
        return nifflerUserDataService.updateUserInfo(userDataJson).execute().body();
    }

}
