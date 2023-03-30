package com.kfxlabs.smartsociety.model;

import com.google.gson.annotations.SerializedName;

public class PumpMetaDetails {

    @SerializedName("p_name")
    public String pumpName;

    @SerializedName("p_make")
    public String pMake;

    @SerializedName("p_DevId")
    public String pDevId;


    @SerializedName("p_pin")
    public String ppin;

    @SerializedName("p_location")
    public String pLocation;

    @SerializedName("p_kva")
    public String pkva;

    public PumpMetaDetails(String pName, String pMake, String pDevId, String ppin, String pLocation, String pkva) {
        this.pumpName = pName;
        this.pMake = pMake;
        this.pDevId = pDevId;
        this.ppin = ppin;
        this.pLocation = pLocation;
        this.pkva = pkva;


    }
}
