package com.kfxlabs.smartsociety.api;

import com.kfxlabs.smartsociety.module.InfoPost;
import com.kfxlabs.smartsociety.module.LoginPost;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface login {

    @POST("/login")
    @FormUrlEncoded
    Call<String> savePost(@Field("androidID") String androidID,
                            @Field("uid") String uid,
                            @Field("pwd") String pwd);

}
