package com.kfxlabs.smartsociety.module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SettingPumpGet {

    @SerializedName("pmake")
    @Expose
    private String pmake;

    @SerializedName("pmodel")
    @Expose
    private String pmodel;

    @SerializedName("prating")
    @Expose
    private String prating;

    @SerializedName("prunmax")
    @Expose
    private String prunmax;

    @SerializedName("troutine")
    @Expose
    private String troutine;

    @SerializedName("toverhaul")
    @Expose
    private String toverhaul;




    public void setPmake(String pmake) {
        this.pmake = pmake;
    }

    public void setPmodel(String pmodel) {
        this.pmodel = pmodel;
    }

    public void setPrating(String prating) {
        this.prating = prating;
    }

    public void setPrunmax(String prunmax) { this.prunmax = prunmax; }

    public void setToverhaul(String toverhaul) {
        this.toverhaul = toverhaul;
    }

    public void setTroutine(String troutine) {
        this.troutine = troutine;
    }

    public String getPmake() {
        return pmake;
    }

    public String getPmodel() {
        return pmodel;
    }

    public String getPrating() {
        return prating;
    }

    public String getPrunmax() {
        return prunmax;
    }

    public String getToverhaul() {
        return toverhaul;
    }

    public String getTroutine() {
        return troutine;
    }
}

