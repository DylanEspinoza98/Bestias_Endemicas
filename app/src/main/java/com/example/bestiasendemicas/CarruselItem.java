package com.example.bestiasendemicas;
import androidx.annotation.DrawableRes;

public class CarruselItem {
    private final int imageResource;
    private final String zoneName;
    private final Class<?> activityToOpen;
    private final int backgroundImageResource;

    public CarruselItem(int imageResource, String zoneName, Class<?> activityToOpen, @DrawableRes int backgroundImageResource) {
        /**Constructor*/
            this.imageResource = imageResource;
            this.zoneName = zoneName;
            this.activityToOpen = activityToOpen;
            this.backgroundImageResource = backgroundImageResource;
        }

        /**Getters*/
        public int getImageResource () {
            return imageResource;
        }

        public String getZoneName () {
            return zoneName;
        }

        public Class<?> getActivityToOpen () {
            return activityToOpen;
        }
        public int getBackgroundImageResource () {
            return backgroundImageResource;
        }
    }
