package com.kfxlabs.smartsociety.api;

import com.kfxlabs.smartsociety.module.AddDgFuelPost;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AddDgFuel {

    @POST("/add_fuel")
    @FormUrlEncoded
    Call<AddDgFuelPost> savePost(@Field("androidID") String androidID,
                                 @Field("devid") String devid,
                                 @Field("date") String date,
                                 @Field("time") String time,
                                 @Field("fadd") String fadd,
                                 @Field("fval") String fval,
                                 @Field("remarks") String remarks,
                                 @Field("prec") String prec);
}
