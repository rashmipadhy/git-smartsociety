package com.kfxlabs.smartsociety.module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class TankPlotDetails {
    @SerializedName("tank_plot")
    @Expose
    public List<TankPlot> tankPlotList;

    public TankPlotDetails() {
    }

    public static class TankPlot {

        @SerializedName("y")
        @Expose
        //public List<Integer> y;
        public int y;

        @SerializedName("x")
        @Expose
        //public List<Long> x;
        public long x;

        @SerializedName("devid")
        @Expose
        public String devid;

        public TankPlot(int y, long x, String devid) {

            this.y = y;
            this.x= x;
            this.devid = devid;


        }
    }
}
