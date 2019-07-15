package com.example.carsonmap;

import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

public class Car {
    @SerializedName("id")
    private int id;
    @SerializedName("plateNumber")
    private String plateNumber;
    @SerializedName("batteryPercentage")
    private int batteryPercentage;
    @SerializedName("batteryEstimatedDistance")
    private double batteryEstimatedDistance;
    @SerializedName("isCharging")
    private boolean isCharging;
    @SerializedName("location")
    private CarLocation carLocation;
    @SerializedName("model")
    private CarModel carModel;
    private float distanceFromYou;

    public class CarLocation{
        @SerializedName("id")
        private int id;
        @SerializedName("latitude")
        private double latitude;
        @SerializedName("longitude")
        private double longtitude;
        @SerializedName("address")
        private String address;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongtitude() {
            return longtitude;
        }

        public void setLongtitude(double longtitude) {
            this.longtitude = longtitude;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    public static class CarModel{
        @SerializedName("id")
        private int id;
        @SerializedName("title")
        private String title;
        @SerializedName("photoUrl")
        private String photoUrl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPhotoUrl() {
            return photoUrl;
        }

        public void setPhotoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
        }
    }

    public float getDistanceFromYou() {
        return distanceFromYou;
    }

    public void setDistanceFromYou(float distanceFromYou) {
        this.distanceFromYou = distanceFromYou;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public int getBatteryPercentage() {
        return batteryPercentage;
    }

    public void setBatteryPercentage(int batteryPercentage) {
        this.batteryPercentage = batteryPercentage;
    }

    public double getBatteryEstimatedDistance() {
        return batteryEstimatedDistance;
    }

    public void setBatteryEstimatedDistance(double batteryEstimatedDistance) {
        this.batteryEstimatedDistance = batteryEstimatedDistance;
    }

    public boolean isCharging() {
        return isCharging;
    }

    public void setCharging(boolean charging) {
        isCharging = charging;
    }

    public CarLocation getCarLocation() {
        return carLocation;
    }

    public void setCarLocation(CarLocation carLocation) {
        this.carLocation = carLocation;
    }

    public CarModel getCarModel() {
        return carModel;
    }

    public void setCarModel(CarModel carModel) {
        this.carModel = carModel;
    }
}
