package com.kfxlabs.smartsociety.api;

import com.kfxlabs.smartsociety.module.InflowLogDetails;
import com.kfxlabs.smartsociety.module.OutflowLogDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetInflowLog {

    @GET("/inflow_log")
    public Call<InflowLogDetails> savePost(@Query("devid") String devid);

}
