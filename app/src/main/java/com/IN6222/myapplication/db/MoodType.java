package com.IN6222.myapplication.db;

/**
 * Mood Type
 */
public class MoodType {
    int id;
    String description;
    int imageId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public MoodType() {
    }

    public MoodType(int id, String description, int imageId) {
        this.id = id;
        this.description = description;
        this.imageId = imageId;
    }
}
