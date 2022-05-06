package com.kfxlabs.smartsociety.api;

import com.kfxlabs.smartsociety.module.RegistrationPost;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserRegistration {


    @POST("/register")
    @FormUrlEncoded
    Call<RegistrationPost> savePost(@Field("androidID") String androidID,
                                    @Field("pwd") String pwd,
                                    @Field("uname") String uname,
                                    @Field("uphone") String uphone,
                                    @Field("umail") String umail,
                                    @Field("orgid") String orgid);
}
