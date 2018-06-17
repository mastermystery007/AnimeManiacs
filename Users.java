package com.doodlz.husain.animemaniacs;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by husai on 08-02-2018.
 */

public  class Users {




    private static String userName;
    private static String bio;
    private static String profilePicture;
    private static  String emailID;


    private DatabaseReference userDBR=FirebaseDatabase.getInstance().getReference().child("Users");






    public Users(String firebaseUserId) {

        this.setUserName(userDBR.child(firebaseUserId).child("userName").toString());
        this.setBio(userDBR.child(firebaseUserId).child("bio").toString());
        this.setProfilePicture(userDBR.child(firebaseUserId).child("userName").toString());

    }

    public Users(String userName,String bio,String profilePicture,String emailID){
        this.setUserName(userName);
        this.setProfilePicture(profilePicture);
        this.setBio(bio);
        this.setEmailID(emailID);
    }


    public Users(){}

    public static String getEmailID() {
        return emailID;
    }

    public static void setEmailID(String emailID) {
        Users.emailID = emailID;
    }

    public  static  String getUserName() {
        return userName;
    }

    public static void setUserName(String userNamee) {
        userName = userNamee;
    }




    public static String getBio() {
        return bio;
    }

    public static void setBio(String bioo) {bio = bioo;}




    public static String getProfilePicture() {
        return profilePicture;
    }

    public static void setProfilePicture(String profilePicture) {
        profilePicture = profilePicture;
    }
}
