package com.doodlz.husain.animemaniacs;

/**
 * Created by husai on 09-03-2018.
 */

public class Description {
    String descriptionContent;
    int upvotes;
    String userName;
    String anime;
    String uploaderId;

    public Description(){}


    public Description(String descriptionContent, int upvotes, String userName, String anime,String uploaderId) {
        this.descriptionContent = descriptionContent;
        this.upvotes = upvotes;
        this.uploaderId= uploaderId;
        this.userName = userName;
        this.anime = anime;
    }

    public String getDescriptionContent() {
        return descriptionContent;
    }

    public void setDescriptionContent(String descriptionContent) {
        this.descriptionContent = descriptionContent;}

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

    public String getAnime() {
        return anime;
    }

    public void setAnime(String anime) {
        this.anime = anime;
    }

    public String getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(String uploaderId) {
        this.uploaderId = uploaderId;
    }
}
