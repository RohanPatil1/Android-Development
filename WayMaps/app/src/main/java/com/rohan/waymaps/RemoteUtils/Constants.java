package com.rohan.waymaps.RemoteUtils;

public class Constants {

    public static final String GOOGLE_API_URL = "https://maps.googleapis.com/";

    public static GMapServices getGoogleServices() {
        return RetrofitClient.getClient(GOOGLE_API_URL).create(GMapServices.class);
    }

}
