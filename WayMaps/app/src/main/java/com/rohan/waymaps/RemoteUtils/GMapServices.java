package com.rohan.waymaps.RemoteUtils;

import com.rohan.waymaps.pojo.MyPlaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface GMapServices {

    @GET
    Call<MyPlaces> getNearByPlaces(@Url String url);


}
