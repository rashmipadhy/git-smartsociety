package com.kfxlabs.smartsociety.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DgMetaDetails implements Serializable {

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

    @SerializedName("dg_kva")
    public String dgkva;

    @SerializedName("dg_Reading")
    public String dgReading;



    public DgMetaDetails(String dgName, String dgMake, String dgDevId, String dgpin, String dgLocation, String dgkva, String dgReading) {
        this.dgName = dgName;
        this.dgMake = dgMake;
        this.dgDevId = dgDevId;
        this.dgpin = dgpin;
        this.dgLocation = dgLocation;
        this.dgkva = dgkva;
        this.dgReading = dgReading;

    }
}
