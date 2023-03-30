package com.kfxlabs.smartsociety.api;

import com.kfxlabs.smartsociety.module.Dglist;
import com.kfxlabs.smartsociety.module.Pumplist;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetPumplist {


    @GET("/pump_list")
    Call<Pumplist> savePost();

}
