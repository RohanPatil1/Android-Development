package com.rohan.android.myapplication;

public class SubjectModel {

    String subjectTitle;
    int imageID;

    public SubjectModel(String subjectTitle, int imageID) {
        this.subjectTitle = subjectTitle;
        this.imageID = imageID;
    }

    public String getSubjectTitle() {
        return subjectTitle;
    }

    public void setSubjectTitle(String subjectTitle) {
        this.subjectTitle = subjectTitle;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }


}
