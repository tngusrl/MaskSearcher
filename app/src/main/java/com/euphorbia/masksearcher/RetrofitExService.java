package com.euphorbia.masksearcher;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface RetrofitExService {

    String URL = "https://8oi9s0nnth.apigw.ntruss.com/";

    @Headers({"Accept: application/json"})
    @GET("corona19-masks/v1/storesByAddr/json")
    Call<Data> get_Store_retrofit(@Query("address") String address);

}
