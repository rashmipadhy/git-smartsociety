package com.kfxlabs.smartsociety.model;

import com.google.gson.annotations.SerializedName;

public class TankValueDetails {

    @SerializedName("tank_name")
    public String tank_name;

    @SerializedName("dev_stat")
    public String dev_stat;

    @SerializedName("tank_level")
    public String tank_level;

    @SerializedName("uname")
    public String uname;

    @SerializedName("alerts")
    public String alerts;

    @SerializedName("capacity")
    public String capacity;

    @SerializedName("quality")
    public String quality;

    @SerializedName("doutflow")
    public String doutflow;

    @SerializedName("dinflow")
    public String dinflow;

    @SerializedName("tank_val")
    public String tank_val;

    @SerializedName("devid")
    public String devid;






    public TankValueDetails(String tank_name, String dev_stat, String tank_level, String alerts,String capacity, String quality, String doutflow,String dinflow, String tank_val,String devid ) {

        this.tank_name = tank_name;
        this.dev_stat = dev_stat;
        this.tank_level = tank_level;
        this.alerts = alerts;
        this.capacity = capacity;
        this.quality = quality;
        this.doutflow = doutflow;
        this.dinflow = dinflow;
        this.tank_val = tank_val;
        this.devid = devid;
        }

}
