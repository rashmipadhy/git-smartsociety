package com.kfxlabs.smartsociety.module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetDglist {

    @SerializedName("dg_name")
    public String dgName;

    @SerializedName("dg_make")
    public String dgMake;

    @SerializedName("dg_DevId")
    public String dgDevId;


    @SerializedName("dg_pin")
    public String dgpin;

    @SerializedName("dg_location")
    public String dgLocation;



    public String getDgName() {
        return dgName;
    }

    public String getDgMake() {
        return dgMake;
    }

    public String getDgLocation() {
        return dgLocation;
    }


}
