package com.hossam.emergency.gps_provider;

public class LocationModel {

    private double latitude, longitude;
    private long timestamp;

    public LocationModel(double latitude, double longitude, long timestamp) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
    }

    public LocationModel() {
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
