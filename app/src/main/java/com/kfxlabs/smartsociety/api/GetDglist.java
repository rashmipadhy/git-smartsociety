package com.kfxlabs.smartsociety.api;

import com.kfxlabs.smartsociety.module.Dglist;
import com.kfxlabs.smartsociety.module.SettingGet;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDglist {

    @GET("/dg_list")
    Call<Dglist> savePost();

}
