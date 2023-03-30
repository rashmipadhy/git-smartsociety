package com.kfxlabs.smartsociety.api;

import com.kfxlabs.smartsociety.module.OutflowLogDetails;
import com.kfxlabs.smartsociety.module.PowerLogDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetOutflowrLog {

    @GET("/outflow_log")
    public Call<OutflowLogDetails> savePost(@Query("devid") String devid);

}
