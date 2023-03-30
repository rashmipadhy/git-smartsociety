package com.kfxlabs.smartsociety.api;

import com.kfxlabs.smartsociety.module.TankPlot;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetTankPlot {

    @GET("/tank_plot")
    Call<TankPlot> savePost(@Query("devid") String devid);

}
