package com.doodlz.husain.animemaniacs;

import java.sql.Time;

/**
 * Created by husai on 08-02-2018.
 */

public class Predictions {

    String predictionContent;
    int upvotes, downvotes;
    String userName;
    String anime;


    public Predictions() {}


    public Predictions(int downvotes,String predictionContent, int upvotes, String userName) {
        this.downvotes=downvotes;
        this.upvotes=upvotes;
        this.predictionContent=predictionContent;
        this.userName=userName;
    }

    public String getPredictionContent() {
        return predictionContent;
    }

    public void setPredictionContent(String predictionContent) {
        this.predictionContent = predictionContent;
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

    public String getAnime() {return anime;}


    public void setAnime(String anime) {
        this.anime = anime;
    }



    public String showData(){
        return this.predictionContent+" "+this.upvotes+" "+this.downvotes+" "+this.userName;

    }

}