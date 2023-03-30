package com.kfxlabs.smartsociety.model;

import com.google.gson.annotations.SerializedName;

public class PumpValueDetails {

    @SerializedName("pDevId")
    public String pDevId;

    @SerializedName("pVoltage")
    public String pVoltage;

    @SerializedName("uname")
    public String uname;

    @SerializedName("pwrstatus")
    public String pwrStatus;

    @SerializedName("pName")
    public String pName;

    @SerializedName("pStatus")
    public String pStatus;

    @SerializedName("pAlert")
    public String pAlert;

    @SerializedName("cname")
    public String cname;

    @SerializedName("phealth")
    public String phealth;

    @SerializedName("pDevStatus")
    public String pDevstatus;

    @SerializedName("pRuntime")
    public String pRuntime;



    public PumpValueDetails(String pDevId, String pVoltage, String pwrStatus, String pName, String pStatus,String pAlert, String phealth, String pDevstatus, String pRuntime) {

        this.pDevId = pDevId;
        this.pVoltage = pVoltage;

        this.pwrStatus = pwrStatus;
        this.pName = pName;
        this.pStatus = pStatus;
        this.pAlert = pAlert;

        this.phealth = phealth;
        this.pDevstatus = pDevstatus;
        this.pRuntime = pRuntime;
    }


}
