package com.kfxlabs.smartsociety.api;

import com.kfxlabs.smartsociety.module.PowerLogDetails;
import com.kfxlabs.smartsociety.module.PumpPowerLogDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetPumpPowerLog {

    @GET("/power_log")
    public Call<PumpPowerLogDetails> savePost(@Query("devid") String devid);

}
