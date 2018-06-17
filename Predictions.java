package com.doodlz.husain.animemaniacs;

import java.sql.Time;

/**
 * Created by husai on 08-02-2018.
 */

public class Predictions {

    String predictionContent;
    int upvotes;
    String userName;
    String anime;
    String uploaderId;


    public Predictions() {}


    public Predictions(String uploaderId,String predictionContent, int upvotes, String userName) {
        this.uploaderId=uploaderId;
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


    public String getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(String uploaderId) {
        this.uploaderId = uploaderId;
    }

    public String showData(){
        return this.predictionContent+" "+this.upvotes+" "+this.uploaderId+" "+this.userName;

    }

}