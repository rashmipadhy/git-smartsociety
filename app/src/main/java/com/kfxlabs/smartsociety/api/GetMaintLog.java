package com.kfxlabs.smartsociety.api;

import com.kfxlabs.smartsociety.module.FuelLogDetails;
import com.kfxlabs.smartsociety.module.MaintLogDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetMaintLog {

    @GET("/maint_log")
    public Call<MaintLogDetails> savePost(@Query("devid") String devid);

}
