package com.doodlz.husain.animemaniacs;

import java.util.ArrayList;

/**
 * Created by husai on 08-02-2018.
 */

public  class Users {


    private static String userName;
    private static String userID;
    private static String bio;
    private static int profilePicture;

    public  String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        userName = userName;
    }

    public static String getUserID() {
        return userID;
    }

    public static void setUserID(String userID) {
        userID = userID;
    }

    public static String getBio() {
        return bio;
    }

    public static void setBio(String bio) {
        bio = bio;
    }

    public static int getProfilePicture() {
        return profilePicture;
    }

    public static void setProfilePicture(int profilePicture) {
        profilePicture = profilePicture;
    }
}
