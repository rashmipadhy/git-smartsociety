package com.kfxlabs.smartsociety.api;

import com.kfxlabs.smartsociety.module.AddDgPost;
import com.kfxlabs.smartsociety.module.AddPumpPost;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AddPump {

    @POST("/add_pump")
    @FormUrlEncoded
    Call<AddPumpPost> savePost(@Field("ploc") String ploc,
                               @Field("pname") String pname,
                               @Field("devid") String devid,
                               @Field("devpin") String devpin,
                               @Field("pmake") String pmake,
                               @Field("pmodel") String pmodel,
                               @Field("prating") String prating,
                               @Field("prunmax") String prunmax);
}

