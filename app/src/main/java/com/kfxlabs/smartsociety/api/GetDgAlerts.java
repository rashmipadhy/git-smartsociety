package com.kfxlabs.smartsociety.api;

import com.kfxlabs.smartsociety.module.Alerts;
import com.kfxlabs.smartsociety.module.DgAlerts;
import com.kfxlabs.smartsociety.module.FuelLogDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDgAlerts {

    @GET("/dev_alerts")
    Call<DgAlerts> savePost(@Query("devid") String devid);

}
