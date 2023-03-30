package com.kfxlabs.smartsociety.module;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Pumplist {

    @SerializedName("devid")
    public  List<String> devid;

    @SerializedName("voltages")
    public List<String> voltages;

    @SerializedName("uname")
    public String uname;

    @SerializedName("pwr_stat")
    public  List<String> pwr_stat;

    @SerializedName("pump_name")
    public  List<String> pump_name;

    @SerializedName("pump_stat")
    public  List<String> pump_stat;

    @SerializedName("alerts")
    public  List<String> alerts;

    @SerializedName("cname")
    public String cname;

    @SerializedName("health")
    public List<String> health;

    @SerializedName("dev_stat")
    public List<String> dev_stat;

    @SerializedName("runtime")
    public List<String> run_time;

}
