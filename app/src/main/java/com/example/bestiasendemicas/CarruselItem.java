package com.example.bestiasendemicas;

public class CarruselItem {
    private final int imageResource;
    private final String zoneName;
    private final Class<?> activityToOpen;

    /**Constructor*/
    public CarruselItem(int imageResource, String zoneName, Class<?> activityToOpen) {
        this.imageResource = imageResource;
        this.zoneName = zoneName;
        this.activityToOpen = activityToOpen;
    }

    /**Getters*/
    public int getImageResource() {
        return imageResource;
    }

    public String getZoneName() {
        return zoneName;
    }

    public Class<?> getActivityToOpen() {
        return activityToOpen;
    }
}
