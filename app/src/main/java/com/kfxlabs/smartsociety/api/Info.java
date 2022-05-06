package com.kfxlabs.smartsociety.api;

import com.kfxlabs.smartsociety.module.InfoPost;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Info {


        @POST("/profile")
        @FormUrlEncoded
        Call<InfoPost> savePost(@Field("androidID") String androidID,
                                @Field("phone") String app,
                                @Field("uphone") String uphone);



}
