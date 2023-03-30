package com.kfxlabs.smartsociety.module;

import com.google.gson.annotations.SerializedName;
import com.kfxlabs.smartsociety.model.Alerts_Details;

import java.util.List;

public class Tanklist {

    @SerializedName("tank_name")
    public List<String> tank_name;

    @SerializedName("dev_stat")
    public List<String> dev_stat;

    @SerializedName("tank_level")
    public List<String> tank_level;

    @SerializedName("uname")
    public String uname;

    @SerializedName("alerts")
    public List<String> alerts;

    @SerializedName("capacity")
    public List<String> capacity;

    @SerializedName("quality")
    public  List<String> quality;

    @SerializedName("doutflow")
    public  List<String> doutflow;

    @SerializedName("dinflow")
    public  List<String> dinflow;

    @SerializedName("tank_val")
    public  List<String> tank_val;

    @SerializedName("devid")
    public  List<String> devid;

    @SerializedName("cname")
    public String cname;

    @SerializedName("flowrate")
    public List<String> flowrate;

    @SerializedName("ph")
    public  List<String> ph;

    @SerializedName("turbidity")
    public  List<String> turbidity;

    @SerializedName("color")
    public List<String> color;
}
