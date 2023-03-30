package com.kfxlabs.smartsociety.api;

import com.kfxlabs.smartsociety.module.FuelLogDetails;
import com.kfxlabs.smartsociety.module.RunLogDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetRunLog {

    @GET("/dgrun_log")
    public Call<RunLogDetails> savePost(@Query("devid") String devid);

}
