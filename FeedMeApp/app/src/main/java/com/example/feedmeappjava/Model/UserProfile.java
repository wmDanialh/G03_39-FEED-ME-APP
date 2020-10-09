package com.example.feedmeappjava.Model;

public class UserProfile {

    public String userMobile;
    public String userName;
    public String userEmail;

    public UserProfile(String userMobile, String userName, String userEmail) {
        this.userMobile = userMobile;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public UserProfile() {
    }


    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

}
