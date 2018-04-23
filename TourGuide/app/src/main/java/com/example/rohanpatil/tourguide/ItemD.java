package com.example.rohanpatil.tourguide;

/**
 * Created by Rohan Patil on 20-04-2018.
 */

public class ItemD {


    private String mname;
    private int mImageID;

    ItemD(String title,int mImageResourceID){
    mname= title;
    mImageID = mImageResourceID;
    }

 public String getName(){return mname;}
 public int getmImageID(){return mImageID;}

}
