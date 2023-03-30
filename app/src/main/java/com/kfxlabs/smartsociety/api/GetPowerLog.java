package com.kfxlabs.smartsociety.api;

import com.kfxlabs.smartsociety.module.FuelLogDetails;
import com.kfxlabs.smartsociety.module.PowerLogDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetPowerLog {

    @GET("/power_log")
    public Call<PowerLogDetails> savePost(@Query("devid") String devid);

}
