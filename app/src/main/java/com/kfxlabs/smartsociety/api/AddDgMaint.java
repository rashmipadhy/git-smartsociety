package com.kfxlabs.smartsociety.api;

import com.kfxlabs.smartsociety.module.AddDgFuelPost;
import com.kfxlabs.smartsociety.module.AddDgMaintPost;
import com.kfxlabs.smartsociety.module.AddDgPost;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AddDgMaint {

    @POST("/add_maint")
    @FormUrlEncoded
    Call<AddDgMaintPost> savePost(@Field("androidID") String androidID,
                               //   @Field("app") String app,
                                  @Field("devid") String devid,
                                  @Field("date") String date,
                                  @Field("mtype") String mtype,
                                  @Field("ehours") String ehours,
                                  @Field("remarks") String remarks);
}
