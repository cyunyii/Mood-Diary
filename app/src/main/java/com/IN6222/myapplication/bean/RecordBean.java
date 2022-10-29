package com.IN6222.myapplication.bean;

public class RecordBean {
    int id;
    String mood;
    int imgId;
    String title;
    String content;
    String date;
    int year;
    int month;

    public RecordBean() {
    }

    public RecordBean(int id, String mood, int imgId, String title, String content, String date, int year, int month) {
        this.id = id;
        this.mood = mood;
        this.imgId = imgId;
        this.title = title;
        this.content = content;
        this.date = date;
        this.year = year;
        this.month = month;
    }

    public int getId() {
        return id;
    }

    public String getMood() {
        return mood;
    }

    public int getImgId() {
        return imgId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
