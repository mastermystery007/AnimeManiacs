package com.doodlz.husain.animemaniacs;

/**
 * Created by husai on 28-05-2018.
 */

public class UPopinion {
    String content,userName,type;
    int upvotes;

    public UPopinion() {}

    public UPopinion(int upvotes, String content, String userName, String type) {
        this.upvotes = upvotes;
        this.content = content;
        this.userName = userName;
        this.type = type;
    }


    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {this.type = type;}
}
