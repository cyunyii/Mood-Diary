package com.IN6222.myapplication.bean;

public class chartBean {

    String MoodType;
    int count;
    int imgId;

    public chartBean(String moodType, int count, int imgId) {
        MoodType = moodType;
        this.count = count;
        this.imgId = imgId;
    }

    public String getMoodType() {
        return MoodType;
    }

    public int getCount() {
        return count;
    }


    public int getImgId() {
        return imgId;
    }

    public void setMoodType(String moodType) {
        MoodType = moodType;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
