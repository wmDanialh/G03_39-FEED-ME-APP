package com.example.feedmeappjava.Model;

public class Rating {

    private String userPhoneString;
    private String foodId;
    private String rateValue;
    private String comment;

    public Rating(){

    }

    public Rating(String userPhoneString, String foodId, String rateValue, String comment) {
        this.userPhoneString = userPhoneString;
        this.foodId = foodId;
        this.rateValue = rateValue;
        this.comment = comment;
    }

    public String getUserPhoneString() {
        return userPhoneString;
    }

    public void setUserPhoneString(String userPhoneString) {
        this.userPhoneString = userPhoneString;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getRateValue() {
        return rateValue;
    }

    public void setRateValue(String rateValue) {
        this.rateValue = rateValue;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
