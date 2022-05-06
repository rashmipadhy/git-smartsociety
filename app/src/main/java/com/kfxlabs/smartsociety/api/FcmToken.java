package com.kfxlabs.smartsociety.api;

import com.kfxlabs.smartsociety.module.FcmTokenPost;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface FcmToken {

    @POST("/fcm_token")
    @FormUrlEncoded
    Call<FcmTokenPost> savePost(@Field("androidID") String androidID,
                                @Field("uphone") String uphone,
                                @Field("fcmToken") String fcmToken);

}
