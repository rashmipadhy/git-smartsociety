package com.kfxlabs.smartsociety.module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RunLogDetails {

    @SerializedName("dgrun_log")
    @Expose
    public List<RunLog> runLogList;

    public RunLogDetails() {
    }

    public static class RunLog {

        @SerializedName("date")
        @Expose
        public String date;

        @SerializedName("info")
        @Expose
        public String info;

        @SerializedName("dur")
        @Expose
        public String dur;
        @SerializedName("time")
        @Expose
        public String time;


        public RunLog(String date, String info, String dur, String time) {
            this.date = date;
            this.info = info;
            this.dur = dur;
            this.time = time;

        }
    }
}
