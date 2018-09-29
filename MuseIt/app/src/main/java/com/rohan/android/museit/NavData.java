package com.rohan.android.museit;

import java.util.ArrayList;
import java.util.List;

public class NavData {
    private int imageId;
    private String name;

    public NavData() {
        //Constructor
    }

    public NavData(int imageId, String name) {
        this.imageId = imageId;
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<NavData> getData() {
        List<NavData> dataList = new ArrayList<>();

        int[] images = getImages();
        String[] names = getNames();

        for (int i = 0; i < names.length; i++) {
            NavData navItem = new NavData();
            navItem.setImageId(images[i]);
            navItem.setName(names[i]);
            dataList.add(navItem);
        }
        return dataList;
    }

    private static int[] getImages() {
        return new int[]{
                R.drawable.navplay, R.drawable.playlist,
                R.drawable.shuffle, R.drawable.settings,
        };
    }


    private static String[] getNames() {
        return new String[]{
                "Now Playing", "Playlist", "Shuffle", "Settings"
        };
    }
}
