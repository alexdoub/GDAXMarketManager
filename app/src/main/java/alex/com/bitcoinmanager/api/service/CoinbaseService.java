package alex.com.bitcoinmanager.api.service;

import alex.com.bitcoinmanager.api.service.response.GetPricesServiceResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Alex on 11/3/2017.
 */

public interface CoinbaseService {
    @GET("/users/repos")
    Call<GetPricesServiceResponse> getPrices();
}
