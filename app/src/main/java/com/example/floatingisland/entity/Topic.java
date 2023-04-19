package com.example.floatingisland.entity;

public class Topic {

    int tid;
    String tname;
    String timageurl;
    String tsignature;

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getTimageurl() {
        return timageurl;
    }

    public void setTimageurl(String timageurl) {
        this.timageurl = timageurl;
    }

    public String getTsignature() {
        return tsignature;
    }

    public void setTsignature(String tsignature) {
        this.tsignature = tsignature;
    }

    int tpostsnum;

    public int getTpostsnum() {
        return tpostsnum;
    }

    public void setTpostsnum(int tpostsnum) {
        this.tpostsnum = tpostsnum;
    }
}
