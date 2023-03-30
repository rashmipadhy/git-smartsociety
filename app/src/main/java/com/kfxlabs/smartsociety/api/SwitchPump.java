package com.kfxlabs.smartsociety.api;

import com.kfxlabs.smartsociety.module.SwitchPumpPost;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SwitchPump {

    @POST("/control_pump")
    @FormUrlEncoded
    Call<SwitchPumpPost> savePost(@Field("androidID") String androidID,
                                  @Field("devid") String devid,
                                  @Field("state") String state

    );
}
