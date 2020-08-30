package com.example.feedmeappjava.Model;

public class UserProfile {

    public String userMobile;
    public String userName;
    public String userEmail;
    public String userPassword;

    public UserProfile(String userMobile, String userName, String userEmail, String userPassword) {
        this.userMobile = userMobile;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public UserProfile() {
    }

    public UserProfile(String name, String email, String mobile) {
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

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
