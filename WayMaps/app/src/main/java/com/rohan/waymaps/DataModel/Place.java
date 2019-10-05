package com.rohan.waymaps.DataModel;

public class Place {
    double lat;
    double lng;
    String placeName;
    String vicinity;
    int rating;
    String imgUrl;


    public Place(double lat, double lng, String placeName, String vicinity, int rating, String imgUrl) {

        this.lat = lat;
        this.lng = lng;
        this.placeName = placeName;
        this.vicinity = vicinity;
        this.rating = rating;
        this.imgUrl = imgUrl;

    }

    public String getPlaceName() {
        return placeName;
    }

    public int getPlaceRating() {
        return rating;
    }


    public double getLAT() {
        return lat;
    }


    public double getLNG() {
        return lng;
    }


    public String getImgUrl() {
        return imgUrl;
    }

    public String getVicinity(){
        return vicinity;
    }

}
