package com.doodlz.husain.animemaniacs;

import java.sql.Time;

/**
 * Created by husai on 08-02-2018.
 */

public class Predictions {

    String pred_content;
    int upvotes,downvotes;
    String userName;
    String anime;
    Time time;


    public String getPred_content() {
        return pred_content;
    }

    public void setPred_content(String pred_content) {
        this.pred_content = pred_content;
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
