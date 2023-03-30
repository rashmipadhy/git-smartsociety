package com.kfxlabs.smartsociety.api;

import com.kfxlabs.smartsociety.module.SettingPost;
import com.kfxlabs.smartsociety.module.SettingPumpPost;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PostPumpSettings {
    @POST("/pump_settings")
    @FormUrlEncoded
    Call<SettingPumpPost> savePost(@Field("androidID") String androidID,
                                   @Field("devid") String devid,
                                   @Field("pmake") String pmake,
                                   @Field("pmodel") String pmodel,
                                   @Field("prating") String prating,
                                   @Field("prunmax") String prunmax,
                                   @Field("troutine") String troutine,
                                   @Field("toverhaul") String toverhaul);


}