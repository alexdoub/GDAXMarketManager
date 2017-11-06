package com.alexdoub.gdaxmanager.api.service;

import java.util.List;

import com.alexdoub.gdaxmanager.api.service.response.GetTimeServiceResponse;
import com.alexdoub.gdaxmanager.api.service.response.OrderBookServiceResponse;
import com.alexdoub.gdaxmanager.models.CurrencyModel;
import com.alexdoub.gdaxmanager.models.ProductModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Alex on 11/3/2017.
 */

public interface GDAXService {
    @GET("/currencies")
    Call<List<CurrencyModel>> getCurrencies();

    @GET("/products")
    Call<List<ProductModel>> getProducts();

    @GET("/products/{product_id}/book")
    Call<OrderBookServiceResponse> getOrderBook(@Path("product_id") String productId, @Query("level") int level);

    @GET("/time")
    Call<GetTimeServiceResponse> getTime();

//    @POST("authenticate")
//    Call<AuthenticateServiceResponse> authenticate(@Body AuthenticateServiceRequest request);
}
