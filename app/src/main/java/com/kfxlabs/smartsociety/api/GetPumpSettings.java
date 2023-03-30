package com.kfxlabs.smartsociety.api;

import com.kfxlabs.smartsociety.module.SettingGet;
import com.kfxlabs.smartsociety.module.SettingPumpGet;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetPumpSettings {
    @GET("/pump_settings")
    Call<SettingPumpGet> savePost(@Query("androidID") String androidID,
                                  @Query("devid") String devid);
}
