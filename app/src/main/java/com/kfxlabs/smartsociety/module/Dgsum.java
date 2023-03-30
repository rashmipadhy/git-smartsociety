package com.kfxlabs.smartsociety.module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dgsum {


    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("devid")
    @Expose
    private String devid;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("loc")
    @Expose
    private String loc;
    @SerializedName("Tremaining")
    @Expose
    private String Tremaining;
    @SerializedName("Tmaint")
    @Expose
    private String Tmaint;
    @SerializedName("fuel")
    @Expose
    private String fuel;
    @SerializedName("health")
    @Expose
    private String health;
    @SerializedName("fuelmax")
    @Expose
    private String fuelmax;
    @SerializedName("alert")
    @Expose
    private String alert;
    @SerializedName("power")
    @Expose
    private String power;
    @SerializedName("offline")
    @Expose
    private String offline;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDevid() {
        return devid;
    }

    public void setDevid(String devid) {
        this.devid = devid;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getTremaining() {
        return Tremaining;
    }

    public void setTremaining(String tremaining) {
        this.Tremaining = tremaining;
    }

    public String getTmaint() {
        return Tmaint;
    }

    public void setTmaint(String tmaint) {
        Tmaint = tmaint;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public void setMaxFuel(String maxFuel) {
        this.fuelmax = maxFuel;
    }

    public String getMaxFuel() {
        return fuelmax;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getPower() {
        return power;
    }

    public void setOffline(String offline) {
        this.offline = offline;
    }

    public String getOffline() {
        return offline;
    }

}

