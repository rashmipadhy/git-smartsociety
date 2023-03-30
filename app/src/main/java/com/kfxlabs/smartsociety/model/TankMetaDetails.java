package com.kfxlabs.smartsociety.model;

import com.google.gson.annotations.SerializedName;

public class TankMetaDetails {

    @SerializedName("t_name")
    public String tankName;

    @SerializedName("t_type")
    public String tType;

    @SerializedName("t_DevId")
    public String tDevId;

    @SerializedName("t_pin")
    public String tPin;

    @SerializedName("t_location")
    public String tLocation;

    @SerializedName("t_capacity")
    public String tCapacity;

    @SerializedName("t_depth")
    public String tDepth;

    @SerializedName("t_offset")
    public String tOffset;


    public TankMetaDetails(String tName, String tType, String tDevId, String tPIn, String tLocation, String tCapacity,String tDepth,String tOffset) {
        this.tankName = tName;
        this.tType = tType;
        this.tDevId = tDevId;
        this.tPin = tPIn;
        this.tLocation = tLocation;
        this.tCapacity = tCapacity;
        this.tDepth = tDepth;
        this.tOffset = tOffset;
    }
}
