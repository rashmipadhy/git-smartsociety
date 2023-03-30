package com.kfxlabs.smartsociety.module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DgInfoPost {


    @SerializedName("uname")
    @Expose
    private String uname;
    @SerializedName("orgid")
    @Expose
    private String orgid;
    @SerializedName("umail")
    @Expose
    private String umail;
    @SerializedName("dgsum")
    @Expose
    private List<Dgsum> dgsum = null;
    @SerializedName("Reply")
    @Expose
    private String reply;

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    public String getUmail() {
        return umail;
    }

    public void setUmail(String umail) {
        this.umail = umail;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public List<Dgsum> getDgsum() {
        return dgsum;
    }

    public void setDgsum(List<Dgsum> dgsum) {
        this.dgsum = dgsum;
    }

    public int dgsumCount() {
        return dgsum.size();
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }
}
