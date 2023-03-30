package com.kfxlabs.smartsociety.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DgValueDetails implements Serializable {

    @SerializedName("dg_name")
    public String dgName;

    @SerializedName("dg_DevId")
    public String dgDevId;

    @SerializedName("tmaint")
    public String tMaint;

    @SerializedName("dg_value")
    public int dgValue;

    @SerializedName("dg_stat")
    public String dgStatus;

    @SerializedName("dev_satus")
    public String devStatus;

    @SerializedName("pwr_stat")
    public String pwrStatus;

    @SerializedName("t_fuel")
    public String tFuel;

    @SerializedName("f_val")
    public String fval;


    public DgValueDetails(String dgName, String dgDevId, String tMaint, int dgValue,String dgStatus,String devStatus,String pwrStatus,String tFuel,String fval) {
        this.dgName = dgName;
        this.dgDevId = dgDevId;
        this.tMaint = tMaint;
        this.dgValue = dgValue;
        this.dgStatus = dgStatus;
        this.devStatus = devStatus;
        this.pwrStatus = pwrStatus;
        this.tFuel = tFuel;
        this.fval = fval;
    }

}
