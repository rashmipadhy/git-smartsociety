package com.kfxlabs.smartsociety.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DgAlerts_Details implements Serializable {

    @SerializedName("date")
    public String date;

    @SerializedName("alerts")
    public String alerts;

    @SerializedName("time")
    public String time;


    public DgAlerts_Details(String date, String alerts, String time) {
        this.date = date;
        this.alerts = alerts;
        this.time = time;

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAlerts() {
        return alerts;
    }

    public void setAlerts(String alerts) {
        this.alerts = alerts;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
