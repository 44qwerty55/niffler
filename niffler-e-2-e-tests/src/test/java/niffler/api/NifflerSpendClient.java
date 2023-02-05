package niffler.api;

import niffler.model.SpendJson;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class NifflerSpendClient {

    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private OkHttpClient okHttpClient = new OkHttpClient
            .Builder()
            .addNetworkInterceptor(interceptor)
            .build();

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(NifflerSpendService.nifflerSpendUri)
            .addConverterFactory(JacksonConverterFactory.create())
            .client(okHttpClient)
            .build();

    private NifflerSpendService nifflerSpendService = retrofit.create(NifflerSpendService.class);

    public SpendJson createSpend(SpendJson spend) throws Exception {
        return nifflerSpendService.addSpend(spend).execute().body();
    }

}
