package com.doodlz.husain.animemaniacs;

import java.sql.Time;

/**
 * Created by husai on 08-02-2018.
 */

public class Predictions {

    String predictionContent;
    String upvotes, downvotes;
    String userName;
    String anime;
    String time;


    public Predictions() {
    }

    public Predictions(String downvotes, String predictionContent, String time, String upvotes, String userName) {
    }

    public String getPredictionContent() {
        return predictionContent;
    }

    public void setPredictionContent(String pred_content) {
        this.predictionContent = pred_content;
    }

    public String getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(String upvotes) {
        this.upvotes = upvotes;
    }

    public String getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(String downvotes) {
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}