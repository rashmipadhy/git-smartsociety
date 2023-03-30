package com.kfxlabs.smartsociety.api;

import com.kfxlabs.smartsociety.module.PumpRunLogDetails;
import com.kfxlabs.smartsociety.module.RunLogDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetPumpRunLog {

    @GET("/pumprun_log")
    public Call<PumpRunLogDetails> savePost(@Query("devid") String devid);

}
