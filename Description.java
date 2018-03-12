package com.doodlz.husain.animemaniacs;

/**
 * Created by husai on 09-03-2018.
 */

public class Description {
    String descriptionContent;
    int upvotes, downvotes;
    String userName;
    String anime;

    public Description(){}


    public Description(String descriptionContent, int upvotes, int downvotes, String userName, String anime) {
        this.descriptionContent = descriptionContent;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
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
}
