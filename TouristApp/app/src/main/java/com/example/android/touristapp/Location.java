package com.example.android.touristapp;



public class Location {
    int imageId;
    String name;
    public Location(){}

    public  Location(int imageId,String name){
        this.imageId=imageId;
        this.name=name;

    }

    public int getImageId() {
        return imageId;
    }

    public String getName() {
        return name;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setName(String name) {
        this.name = name;
    }

}
