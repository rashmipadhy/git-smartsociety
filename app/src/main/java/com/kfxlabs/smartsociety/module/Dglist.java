package com.kfxlabs.smartsociety.module;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Dglist {

    @SerializedName("f_perc")
    public List<Integer> f_perc;

    @SerializedName("tmaint")
    public List<String> tmaint;

    @SerializedName("dg_name")
    public List<String> dg_name;

    @SerializedName("uname")
    public String uname;

    @SerializedName("pwr_stat")
    public List<String> pwr_stat;

    @SerializedName("devid")
    public  List<String> devid;

    @SerializedName("cname")
    public String cname;

    @SerializedName("tfuel")
    public List<String> tfuel;

    @SerializedName("dev_stat")
    public  List<String> dev_stat;

    @SerializedName("dg_stat")
    public  List<String> dg_stat;

    @SerializedName("f_val")
    public List<String> f_val;
}
