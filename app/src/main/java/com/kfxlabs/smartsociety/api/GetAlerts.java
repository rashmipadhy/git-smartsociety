package com.kfxlabs.smartsociety.api;

import com.kfxlabs.smartsociety.model.Alerts_Details;
import com.kfxlabs.smartsociety.module.Alerts;
import com.kfxlabs.smartsociety.module.DgAlerts;
import com.kfxlabs.smartsociety.module.Dglist;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetAlerts {

    @GET("/alerts")
    Call<Alerts> savePost();
}
