package com.kfxlabs.smartsociety.api;

import com.kfxlabs.smartsociety.module.DgInfoPost;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DgInfo {
    @POST("/display")
    @FormUrlEncoded
    Call<DgInfoPost> savePost(@Field("androidID") String androidID,
                              @Field("app") String app,
                              @Field("uphone") String uphone);
}


