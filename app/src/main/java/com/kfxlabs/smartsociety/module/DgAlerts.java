package com.kfxlabs.smartsociety.module;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DgAlerts {

    @SerializedName("date")
    public List<String> date;

    @SerializedName("alerts")
    public List<String> alerts;

    @SerializedName("time")
    public List<String> time;

    @SerializedName("devid")
    public String devid;

/*

    @SerializedName("alerts")
    @Expose
    public List<alerts> alertsList;
    public Alerts() {
    }

    public static class alerts {

        @SerializedName("date")
        @Expose
        public String date;

        @SerializedName("alerts")

        @Expose
        public String alerts;

        @SerializedName("time")
        @Expose
        public String time;

        public alerts(String date, String alerts, String time) {

            this.date = date;
            this.alerts = alerts;
            this.time = time;


        }
    }
*/
}