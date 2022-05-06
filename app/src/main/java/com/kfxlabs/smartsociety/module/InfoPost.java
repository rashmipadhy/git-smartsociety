package com.kfxlabs.smartsociety.module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InfoPost {


    @SerializedName("uname")
    @Expose
    private String uname;
    @SerializedName("orgid")
    @Expose
    private String orgid;
    @SerializedName("umail")
    @Expose
    private String umail;

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


    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

}
