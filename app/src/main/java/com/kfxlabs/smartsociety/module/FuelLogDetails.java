package com.kfxlabs.smartsociety.module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FuelLogDetails  {

    @SerializedName("fuel_log")
    @Expose
    public List<FuelLog> fuelLogList;

    public FuelLogDetails() {
    }

    public static class FuelLog {
        @SerializedName("uid")
        @Expose
        public String uid;
        @SerializedName("time")
        @Expose
        public String time;
        @SerializedName("fval")
        @Expose
        public String fval;
        @SerializedName("date")
        @Expose
        public String date;
        @SerializedName("fadd")
        @Expose
        public String fadd;
        @SerializedName("remarks")
        @Expose
        public String remarks;



        public FuelLog(String uid, String time, String fval, String date, String fadd, String remarks) {
            this.uid = uid;
            this.time = time;
            this.fval = fval;
            this.date = date;
            this.fadd = fadd;
            this.remarks = remarks;
        }
    }
}
