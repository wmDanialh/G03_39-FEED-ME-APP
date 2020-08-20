package com.example.feedmeappjava.Model;

public class FoodCategoryModel {

    private int image;
    private String title;
    private String kinds;

    public FoodCategoryModel(int image, String title, String kinds) {
        this.image = image;
        this.title = title;
        this.kinds = kinds;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKinds() {
        return kinds;
    }

    public void setKinds(String kinds) {
        this.kinds = kinds;
    }

}
