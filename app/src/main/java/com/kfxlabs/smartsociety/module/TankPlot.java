package com.kfxlabs.smartsociety.module;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TankPlot {

    @SerializedName("y")
    public List<Integer> y;

    @SerializedName("x")
    public List<Long> x;

    @SerializedName("devid")
    public List<String> devid;

    @SerializedName("time")
    public List<String> time;

    @SerializedName("date")
    public List<String> date;

}