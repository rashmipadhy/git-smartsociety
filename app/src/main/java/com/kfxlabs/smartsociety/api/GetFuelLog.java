package com.kfxlabs.smartsociety.api;

import com.kfxlabs.smartsociety.module.FuelLogDetails;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetFuelLog {

    @GET("/fuel_log")
    public Call<FuelLogDetails> savePost(@Query("devid") String devid);

}
