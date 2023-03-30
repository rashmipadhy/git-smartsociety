package com.kfxlabs.smartsociety.api;

import com.kfxlabs.smartsociety.module.AddPumpPost;
import com.kfxlabs.smartsociety.module.AddTankPost;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AddTank {


    @POST("/add_tank")
    @FormUrlEncoded
    Call<AddTankPost> savePost(@Field("tloc") String ploc,
                               @Field("tname") String pname,
                               @Field("devid") String devid,
                               @Field("devpin") String devpin,
                               @Field("ttype") String pmake,
                               @Field("tcapacity") String pmodel,
                               @Field("tdepth") String prating,
                               @Field("toffset") String prunmax);
}
