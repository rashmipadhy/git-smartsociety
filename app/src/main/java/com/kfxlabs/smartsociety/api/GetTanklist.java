package com.kfxlabs.smartsociety.api;

import com.kfxlabs.smartsociety.module.Dglist;
import com.kfxlabs.smartsociety.module.Tanklist;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetTanklist {

    @GET("/tank_list")
    Call<Tanklist> savePost();

}
