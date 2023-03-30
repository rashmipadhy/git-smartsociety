package com.kfxlabs.smartsociety.module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InflowLogDetails {

    @SerializedName("inflow_log")
    @Expose
    public List<InflowLog> inflowLogList;

    public InflowLogDetails() {
    }

    public static class InflowLog {

        @SerializedName("date")
        @Expose
        public String date;

        @SerializedName("litres")

        @Expose
        public String litres;

        @SerializedName("time")
        @Expose
        public String time;

        public InflowLog(String date, String litres, String time) {

            this.date = date;
            this.litres = litres;
            this.time = time;


        }
    }
}
