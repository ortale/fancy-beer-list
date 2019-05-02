package uk.co.joseortale.fancybeerlist.data.api.library;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.joseortale.fancybeerlist.AppCons;

/**
 *
 * API client setupd
 *
 */
public class APIClient {

    /**
     *
     * Gets API Client from retrofit framework. On this client the connection timeout, read timeout and the
     * base URL is set.
     *
     * @return
     */
    public static IAPIService getClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS).build();
        return new Retrofit.Builder()
                .baseUrl(AppCons.API_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(IAPIService.class);
    }
}
