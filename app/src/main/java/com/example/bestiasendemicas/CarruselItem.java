package com.example.bestiasendemicas;

public class CarruselItem {
    private final int imageResource;
    private final String zoneName;
    private final Class<?> activityToOpen;

    public CarruselItem(int imageResource, String zoneName, Class<?> activityToOpen) {
        this.imageResource = imageResource;
        this.zoneName = zoneName;
        this.activityToOpen = activityToOpen;
    }

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
