package alex.com.bitcoinmanager.api;

import android.util.Base64;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import alex.com.bitcoinmanager.BitcoinManagerApp;
import alex.com.bitcoinmanager.R;
import alex.com.bitcoinmanager.api.service.GDAXService;
import alex.com.bitcoinmanager.api.service.response.GetTimeServiceResponse;
import alex.com.bitcoinmanager.api.service.response.OrderBookServiceResponse;
import alex.com.bitcoinmanager.interfaces.APICallback;
import alex.com.bitcoinmanager.models.CurrencyModel;
import alex.com.bitcoinmanager.models.OrderModel;
import alex.com.bitcoinmanager.models.ProductModel;
import alex.com.bitcoinmanager.utilities.ViewUtils;
import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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

    public static APIClient getInstance() {
        return _apiClient;
    }

    private GDAXService _GDAXService = null;

    private String _authKey = null;
    private String _authPassphrase = null;
    private String _authSecret = null;


    private APIClient() {

        //Load headers from resources
        _authKey = BitcoinManagerApp.getInstance().getString(R.string.key);
        _authPassphrase = BitcoinManagerApp.getInstance().getString(R.string.passphrase);
        _authSecret = BitcoinManagerApp.getInstance().getString(R.string.secret);

        if (_authKey.length() == 0 || _authPassphrase.length() == 0 || _authSecret.length() == 0) {
            ViewUtils.ShowToast(BitcoinManagerApp.getInstance(), "WARNING - You don't have your auth keys set up. You cannot make authenticated calls!", false);
        }

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {

                        String timestamp = getTimestamp();
                        String method = chain.request().method();
                        String path = chain.request().url().encodedPath();
                        String body = "";
                        if (chain.request().body() != null) {
                            body = chain.request().body().toString();
                        }

                        if (_authKey.length() == 0 || _authPassphrase.length() == 0 || _authSecret.length() == 0) {
                            Timber.i("Skipping authentication for network call " + path + " because auth keys not set up");
                            return chain.proceed(chain.request());
                        }

                        Request updatedRequest = chain.request().newBuilder()
                                .addHeader("CB-ACCESS-KEY", _authKey)
                                .addHeader("CB-ACCESS-SIGN", getBase64SignedDigest(timestamp, method, path, body))
                                .addHeader("CB-ACCESS-TIMESTAMP", timestamp)
                                .addHeader("CB-ACCESS-PASSPHRASE", _authPassphrase)
                                .build();

                        //Check response for authentication issues
                        okhttp3.Response response = chain.proceed(updatedRequest);
                        if (response.code() >= 401 && response.code() <= 403) {
                            ViewUtils.ShowToast(BitcoinManagerApp.getInstance(), "A network call failed authentication. Are your API keys properly set?");
                        }
                        return response;
                    }
                })
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        _GDAXService = retrofit.create(GDAXService.class);
    }

//    public void authenticate() {
//        String key = BitcoinManagerApp.getInstance().GetString(R.string.key);
//    }

    public void getTime() {
        _GDAXService.getTime().enqueue(new Callback<GetTimeServiceResponse>() {
            @Override
            public void onResponse(Call<GetTimeServiceResponse> call, Response<GetTimeServiceResponse> response) {

            }

            @Override
            public void onFailure(Call<GetTimeServiceResponse> call, Throwable t) {

            }
        });
    }

    public void getCurrencies(final APICallback<List<CurrencyModel>> callback) {

        _GDAXService.getCurrencies().enqueue(new Callback<List<CurrencyModel>>() {
            @Override
            public void onResponse(Call<List<CurrencyModel>> call, Response<List<CurrencyModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.success(response.body());
                } else {
                    callback.failure(new Exception("getPrices failure"));
                }
            }

            @Override
            public void onFailure(Call<List<CurrencyModel>> call, Throwable t) {
                Timber.e("getPrices - onFailure()");
                Timber.e(t);
                callback.failure(t);
            }
        });
    }

    public void getProducts(final APICallback<List<ProductModel>> callback) {

        _GDAXService.getProducts().enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.success(response.body());
                } else {
                    callback.failure(new Exception("getPrices failure"));
                }
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                Timber.e("getProducts - onFailure()");
                Timber.e(t);
                callback.failure(t);
            }
        });
    }

    public void getOrderBook(String productId, final APICallback<Observable<OrderModel>> callback) {

        _GDAXService.getOrderBook(productId, 2).enqueue(new Callback<OrderBookServiceResponse>() {
            @Override
            public void onResponse(Call<OrderBookServiceResponse> call, Response<OrderBookServiceResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Observable orders = OrderModel.TransformObservable(response.body());
                    callback.success(orders);
                } else {
                    callback.failure(new Exception("getOrderBook - Failure"));
                }
            }

            @Override
            public void onFailure(Call<OrderBookServiceResponse> call, Throwable t) {
                Timber.e("getOrderBook - Failure");
                Timber.e(t);
                callback.failure(t);
            }
        });
    }


    //HELPER FUNCTIONS
    private String getTimestamp() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String format = simpleDateFormat.format(new Date());
        return format;
    }

    private String getBase64SignedDigest(String timestamp, String method, String requestPath, String body) {

        Timber.i("BEGIN getBase64SignedDigest()");
        Timber.i("timestamp: " + timestamp);
        Timber.i("method: " + method);
        Timber.i("requestPath: " + requestPath);
        Timber.i("body: " + body);

        String concatString = timestamp + method + requestPath + body;
        Timber.i("concatString: " + concatString);

        try {
            String encodedSignedMsg = encode(_authSecret, concatString);
            Timber.i("encodedSignedMsg: [" + encodedSignedMsg + "]");
            return encodedSignedMsg;

        } catch (Exception e) {
            Timber.e(e);
        }
        Timber.e("getBase64SignedDigest() FAILURE!");
        return "";
    }

    public String encode(String key, String data) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);

        byte[] encodedBytes = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
        byte[] encodedBase64 = Base64.encode(encodedBytes, Base64.NO_WRAP);
        String encodedBase64Str = new String(encodedBase64, StandardCharsets.UTF_8);
        return encodedBase64Str;
    }
}
