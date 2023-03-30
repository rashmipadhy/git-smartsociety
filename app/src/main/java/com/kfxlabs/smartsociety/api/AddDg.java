package com.kfxlabs.smartsociety.api;

import com.kfxlabs.smartsociety.module.AddDgPost;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AddDg {

    @POST("/add_dg")
    @FormUrlEncoded
    Call<AddDgPost> savePost(@Field("dgloc") String dgloc,
                             @Field("dgname") String dgname,
                             @Field("devid") String devid,
                             @Field("devpin") String devpin,
                             @Field("dgmake") String dgmake,
                             @Field("dgmodel") String dgmodel,
                             @Field("dgrating") String dgrating,
                             @Field("dghours") String dghours);
}
