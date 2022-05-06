package com.kfxlabs.smartsociety.api;

import com.kfxlabs.smartsociety.module.UPasswordResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Upassword {
    @POST("/password")
    @FormUrlEncoded
    Call<String> savePost(@Field("pwd") String pwd,
                                     @Field("npwd") String npwd);


}
