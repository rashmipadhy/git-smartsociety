package com.kfxlabs.smartsociety.api;

import com.kfxlabs.smartsociety.module.SettingPost;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PostSetting {

    @POST("/dg_settings")
    @FormUrlEncoded
    Call<SettingPost> savePost(@Field("androidID") String androidID,
                               @Field("devid") String devid,
                               @Field("fmax") String fmax,
                               @Field("falert") String falert,
                               @Field("fload") String fload,
                               @Field("troutine") String troutine,
                               @Field("toverhaul") String toverhaul,
                               @Field("toverheat") String toverheat,
                               @Field("dgmake") String dgmake,
                               @Field("dgmodel") String dgmodel,
                               @Field("dgrating") String dgrating);

}
