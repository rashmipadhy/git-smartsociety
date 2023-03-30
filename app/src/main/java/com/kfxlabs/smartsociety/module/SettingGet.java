package com.kfxlabs.smartsociety.module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SettingGet {

    @SerializedName("toverheat")
    @Expose
    private String toverheat;
    @SerializedName("dgrating")
    @Expose
    private String dgrating;
    @SerializedName("falert")
    @Expose
    private String falert;
    @SerializedName("fmax")
    @Expose
    private String fmax;
    @SerializedName("dgmake")
    @Expose
    private String dgmake;
    @SerializedName("toverhaul")
    @Expose
    private String toverhaul;
    @SerializedName("dgmodel")
    @Expose
    private String dgmodel;
    @SerializedName("fload")
    @Expose
    private String fload;
    @SerializedName("troutine")
    @Expose
    private String troutine;

    public void setDgmake(String dgmake) {
        this.dgmake = dgmake;
    }

    public void setDgmodel(String dgmodel) {
        this.dgmodel = dgmodel;
    }

    public void setDgrating(String dgrating) {
        this.dgrating = dgrating;
    }

    public void setFalert(String falert) {
        this.falert = falert;
    }

    public void setFload(String fload) {
        this.fload = fload;
    }

    public void setFmax(String fmax) {
        this.fmax = fmax;
    }

    public void setToverhaul(String toverhaul) {
        this.toverhaul = toverhaul;
    }

    public void setToverheat(String toverheat) {
        this.toverheat = toverheat;
    }

    public void setTroutine(String troutine) {
        this.troutine = troutine;
    }

    public String getDgmake() {
        return dgmake;
    }

    public String getDgmodel() {
        return dgmodel;
    }

    public String getDgrating() {
        return dgrating;
    }

    public String getFalert() {
        return falert;
    }

    public String getFload() {
        return fload;
    }

    public String getFmax() {
        return fmax;
    }

    public String getToverhaul() {
        return toverhaul;
    }

    public String getToverheat() {
        return toverheat;
    }

    public String getTroutine() {
        return troutine;
    }
}
