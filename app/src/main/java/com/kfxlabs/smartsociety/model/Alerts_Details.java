package com.kfxlabs.smartsociety.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Alerts_Details implements Serializable {

    @SerializedName("date")
    public String date;

    @SerializedName("alerts")
    public String alerts;

    @SerializedName("time")
    public String time;



    public Alerts_Details(String date, String alerts, String time) {
        this.date = date;
        this.alerts = alerts;
        this.time = time;

    }

    public void size() {

    }

    public void get(int position) {
    }
}
