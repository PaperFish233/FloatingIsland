package com.example.floatingisland.entity;

public class Posts {
    private int pid;
    private int ptopicid;
    private String paccount;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getPtopicid() {
        return ptopicid;
    }

    public void setPtopicid(int ptopicid) {
        this.ptopicid = ptopicid;
    }

    public String getPaccount() {
        return paccount;
    }

    public void setPaccount(String paccount) {
        this.paccount = paccount;
    }

    private String avatarurl;
    private String content;
    private String imageurl;
    private String topicname;
    private String nickname;
    private String date;

    public String getAvatarurl() {
        return avatarurl;
    }

    public void setAvatarurl(String avatarurl) {
        this.avatarurl = avatarurl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getTopicname() {
        return topicname;
    }

    public void setTopicname(String topicname) {
        this.topicname = topicname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private int likenum;

    public int getLikenum() {
        return likenum;
    }

    public void setLikenum(int likenum) {
        this.likenum = likenum;
    }

    private int topicid;
    private String topicimageurl;
    private String uaccount;

    public int getTopicid() {
        return topicid;
    }

    public void setTopicid(int topicid) {
        this.topicid = topicid;
    }

    public String getTopicimageurl() {
        return topicimageurl;
    }

    public void setTopicimageurl(String topicimageurl) {
        this.topicimageurl = topicimageurl;
    }

    private int collectionnum;
    private int commentnum;

    public int getCollectionnum() {
        return collectionnum;
    }

    public void setCollectionnum(int collectionnum) {
        this.collectionnum = collectionnum;
    }

    public int getCommentnum() {
        return commentnum;
    }

    public void setCommentnum(int commentnum) {
        this.commentnum = commentnum;
    }

    public String getUaccount() {
        return uaccount;
    }

    public void setUaccount(String uaccount) {
        this.uaccount = uaccount;
    }
}
