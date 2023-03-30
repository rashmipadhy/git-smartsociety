package com.kfxlabs.smartsociety.api;

import com.kfxlabs.smartsociety.module.SettingGet;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetSetting {

    @GET("/dg_settings")
    Call<SettingGet> savePost(@Query("androidID") String androidID,
                              @Query("devid") String devid);
}
