package com.kfxlabs.smartsociety.module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MaintLogDetails {

    @SerializedName("maint_log")
    @Expose
    public List<MaintLog> maintLogList;

    public MaintLogDetails() {
    }

    public static class MaintLog {

        @SerializedName("date")
        @Expose
        public String date;
        @SerializedName("mtype")
        @Expose
        public String mtype;
        @SerializedName("ehours")
        @Expose
        public String ehours;
        @SerializedName("uid")
        @Expose
        public String uid;
        @SerializedName("remarks")
        @Expose
        public String remarks;


        public MaintLog(String date, String mtype, String ehours, String uid,String remarks) {

            this.date = date;
            this.mtype = mtype;
            this.ehours = ehours;
            this.uid = uid;
            this.remarks = remarks;
        }
    }
}
