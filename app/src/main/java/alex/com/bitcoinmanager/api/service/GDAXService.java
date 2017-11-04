package alex.com.bitcoinmanager.api.service;

import java.util.List;

import alex.com.bitcoinmanager.api.service.request.AuthenticateServiceRequest;
import alex.com.bitcoinmanager.api.service.response.AuthenticateServiceResponse;
import alex.com.bitcoinmanager.api.service.response.GetTimeServiceResponse;
import alex.com.bitcoinmanager.models.CurrencyModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Alex on 11/3/2017.
 */

public interface GDAXService {
    @GET("/currencies")
    Call<List<CurrencyModel>> getCurrencies();

    @GET("/time")
    Call<GetTimeServiceResponse> getTime();

//    @POST("authenticate")
//    Call<AuthenticateServiceResponse> authenticate(@Body AuthenticateServiceRequest request);
}
