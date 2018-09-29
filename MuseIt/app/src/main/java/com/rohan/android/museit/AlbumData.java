package com.rohan.android.museit;

import java.util.ArrayList;
import java.util.List;

public class AlbumData {
    private int imageId;
    private String album_name;
    private String album_artist;
    private int songId;


    //Getter and Setter
    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public String getAlbum_name() {
        return album_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public String getAlbum_artist() {
        return album_artist;
    }

    public void setAlbum_artist(String album_artist) {
        this.album_artist = album_artist;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    //Looping Thrrough The Data
    public static List<AlbumData> getData() {
        List<AlbumData> dataList = new ArrayList<>();
        int[] images_arr = getImages();
        String[] artists_arr = getArtists();
        String[] names_arr = getNames();

        for (int i = 0; i < images_arr.length; i++) {
            AlbumData item = new AlbumData();
            item.setImageId(images_arr[i]);
            item.setAlbum_name(names_arr[i]);
            item.setAlbum_artist(artists_arr[i]);
            dataList.add(item);
        }
        return dataList;
    }

    // Collecting Data
    private static int[] getImages() {
        return new int[]{
                R.drawable.album1, R.drawable.album2, R.drawable.album3, R.drawable.album4, R.drawable.album5, R.drawable.album6, R.drawable.album7, R.drawable.album8
        };
    }

    private static String[] getArtists() {

        return new String[]{
                "NCS", "BeckyG", "Wiz Khalifa", "Ed Sherran", "DJ Snake", "K'Naan", "Owl City", "Alan Walker'"
        };
    }

    private static String[] getNames() {

        return new String[]{
                "On & On", "Can't Stop Dancin", "See You Again", "Shape Of You", "Magneta Riddim", "Wavin Your Flag", "Shooting Star", "Spectre'"
        };
    }
}
