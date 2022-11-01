package com.IN6222.myapplication.bean;

public class chartBean {
    public chartBean(String moodType, int count, float percentage) {
        MoodType = moodType;
        this.count = count;
        this.percentage = percentage;
    }

    public void setMoodType(String moodType) {
        MoodType = moodType;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public String getMoodType() {
        return MoodType;
    }

    public int getCount() {
        return count;
    }

    public float getPercentage() {
        return percentage;
    }

    String MoodType;
    int count;
    float percentage;
}
