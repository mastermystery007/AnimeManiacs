package com.doodlz.husain.animemaniacs;

import android.media.Image;



public class Animes {

    private String animeName;
    private String description;
    private String image;
    private int range;

    public Animes(){}

    public Animes(String animeName, String description , String image){

        this.animeName=animeName;
        this.description=description;
        this.image=image;
    }

    public String getAnimeName() {
        return animeName;
    }

    public void setAnimeName(String animeName) {
        this.animeName = animeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }
}
