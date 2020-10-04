package com.example.feedmeappjava.Model;

public class CardMainScreen {

    private int imageCard;
    private String titleCard;
    private String descCard;

    public CardMainScreen(int imageCard, String titleCard, String descCard) {
        this.imageCard = imageCard;
        this.titleCard = titleCard;
        this.descCard = descCard;
    }

    public int getImageCard() {
        return imageCard;
    }

    public void setImageCard(int imageCard) {
        this.imageCard = imageCard;
    }

    public String getTitleCard() {
        return titleCard;
    }

    public void setTitleCard(String titleCard) {
        this.titleCard = titleCard;
    }

    public String getDescCard() {
        return descCard;
    }

    public void setDescCard(String descCard) {
        this.descCard = descCard;
    }
}
