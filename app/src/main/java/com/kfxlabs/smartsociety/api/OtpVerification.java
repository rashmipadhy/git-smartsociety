package com.kfxlabs.smartsociety.api;

import com.kfxlabs.smartsociety.module.OtpPost;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface OtpVerification {
    @POST("/otp")
    @FormUrlEncoded
    Call<OtpPost> savePost(@Field("androidID") String androidID,
                           @Field("otp") String otp,
                           @Field("uphone") String uphone);
}


