package com.example.floatingisland.entity;

public class Notice {

    private int nid;
    private String ncontent;
    private String naccount;
    private String nimageurl;
    private String ndate;

    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public String getNcontent() {
        return ncontent;
    }

    public void setNcontent(String ncontent) {
        this.ncontent = ncontent;
    }

    public String getNaccount() {
        return naccount;
    }

    public void setNaccount(String naccount) {
        this.naccount = naccount;
    }

    public String getNimageurl() {
        return nimageurl;
    }

    public void setNimageurl(String nimageurl) {
        this.nimageurl = nimageurl;
    }

    public String getNdate() {
        return ndate;
    }

    public void setNdate(String ndate) {
        this.ndate = ndate;
    }

    private String unickname;
    private String uavatarurl;

    public String getUnickname() {
        return unickname;
    }

    public void setUnickname(String unickname) {
        this.unickname = unickname;
    }

    public String getUavatarurl() {
        return uavatarurl;
    }

    public void setUavatarurl(String uavatarurl) {
        this.uavatarurl = uavatarurl;
    }
}
