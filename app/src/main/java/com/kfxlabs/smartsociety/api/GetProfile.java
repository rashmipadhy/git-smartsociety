package com.kfxlabs.smartsociety.api;

import com.kfxlabs.smartsociety.module.FuelLogDetails;
import com.kfxlabs.smartsociety.module.Profile;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetProfile {

    @GET("/profile")
    Call<Profile> savePost();
}
