package alex.com.bitcoinmanager.api;

import com.facebook.stetho.okhttp3.StethoInterceptor;


import alex.com.bitcoinmanager.api.service.CoinbaseService;
import alex.com.bitcoinmanager.api.service.response.GetPricesServiceResponse;
import alex.com.bitcoinmanager.models.CoinbasePriceModel;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Created by Alex on 11/3/2017.
 */

public class APIClient {

    static String BASE_URL = "https://api-public.sandbox.gdax.com";
//    static String BASE_URL = "https://api.gdax.com";

    private static final APIClient _apiClient = new APIClient();
    public static APIClient getInstance() { return _apiClient; }

    private CoinbaseService _coinbaseService = null;

    private APIClient() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        _coinbaseService = retrofit.create(CoinbaseService.class);
    }

    public void authenticate() {

    }

    public void getPrices(final APICallback<CoinbasePriceModel> callback) {

        _coinbaseService.getPrices().enqueue(new Callback<GetPricesServiceResponse>() {
            @Override
            public void onResponse(Call<GetPricesServiceResponse> call, Response<GetPricesServiceResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.success(response.body().model);
                } else {
                    callback.failure(new Exception("getPrices failure"));
                }
            }

            @Override
            public void onFailure(Call<GetPricesServiceResponse> call, Throwable t) {
                Timber.e(t);
                callback.failure(t);
            }
        });
        System.out.println("did API call to get prices");
    }
}
