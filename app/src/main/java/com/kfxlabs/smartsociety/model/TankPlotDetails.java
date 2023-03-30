package com.kfxlabs.smartsociety.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TankPlotDetails implements Serializable {

    @SerializedName("y")
    public int y_list;

    @SerializedName("x")
    public long x_list;

    @SerializedName("date")
    public String d_list;

    public TankPlotDetails(int y, long x) {
        this.y_list = y;
        this.x_list = x;

    }

}
