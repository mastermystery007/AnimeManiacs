package com.doodlz.husain.animemaniacs;

import java.util.ArrayList;

/**
 * Created by husai on 08-02-2018.
 */

public class Users {

    private String userName;
    private String userID;
    private String bio;
    private int profilePicture;

    public  String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(int profilePicture) {
        this.profilePicture = profilePicture;
    }
}
