package com.example.floatingisland.entity;

public class PostsComment {

    private int cid;
    private int cpostsid;
    private String caccount;
    private String content;
    private String cdate;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getCpostsid() {
        return cpostsid;
    }

    public void setCpostsid(int cpostsid) {
        this.cpostsid = cpostsid;
    }

    public String getCaccount() {
        return caccount;
    }

    public void setCaccount(String caccount) {
        this.caccount = caccount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
    }

    private String avatarurl;
    private String nickname;

    public String getAvatarurl() {
        return avatarurl;
    }

    public void setAvatarurl(String avatarurl) {
        this.avatarurl = avatarurl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
