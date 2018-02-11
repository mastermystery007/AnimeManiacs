package com.doodlz.husain.animemaniacs;

import java.sql.Time;


public class Description {
    String desc_content;
    int upvotes,downvotes;
    String userName;
    String anime;
    Time time;

    public String getDesc_content() {
        return desc_content;
    }

    public void setDesc_content(String desc_content) {
        this.desc_content = desc_content;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAnime() {
        return anime;
    }

    public void setAnime(String anime) {
        this.anime = anime;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
