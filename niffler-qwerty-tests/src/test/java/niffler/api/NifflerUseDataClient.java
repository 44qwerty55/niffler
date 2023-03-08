package niffler.api;

import niffler.api.service.NifflerUserDataService;
import niffler.model.UserDataJson;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class NifflerUseDataClient {

    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private final OkHttpClient okHttpClient = new OkHttpClient
            .Builder()
            .addNetworkInterceptor(interceptor)
            .build();

    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(NifflerUserDataService.nifflerUserDataUri)
            .addConverterFactory(JacksonConverterFactory.create())
            .client(okHttpClient)
            .build();

    private final NifflerUserDataService nifflerUserDataService = retrofit.create(NifflerUserDataService.class);

    public UserDataJson updateUserData(UserDataJson userDataJson) throws Exception {
        return nifflerUserDataService.updateUserInfo(userDataJson).execute().body();
    }

}
