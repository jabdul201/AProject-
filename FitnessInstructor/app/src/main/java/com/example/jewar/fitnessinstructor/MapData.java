package com.example.jewar.fitnessinstructor;

import java.io.Serializable;

/**
 * Created by jewar
 */

public class MapData implements Serializable {

    //declare variables
    private int GymID;
    private String GymAddress;
    private String GymArea;
    private float Latitude;
    private float Longitude;

    private static final long serialVersionUID = 0L;

    public int getGymID() {
        return GymID;
    }

    public void setGymID(int gymID) {
        this.GymID = gymID;
    }

    public String getGymAddress() {
        return GymAddress;
    }

    public void setGymAddress(String gymAddress) {
        this.GymAddress = gymAddress;
    }

    public String getGymArea() {
        return GymArea;
    }

    public void setGymArea(String gymArea) {
        this.GymArea = gymArea;
    }

    public float getLatitude()
    {
        return Latitude;
    }

    public void setLatitude(float Lat)
    {
        this.Latitude = Lat;
    }

    public float getLongitude()
    {
        return Longitude;
    }

    public void setLongitude(float fLongitude)
    {
        this.Longitude = fLongitude;
    }

    @Override
    public String toString() {
        String mapData;
        mapData = "gymMapLocationInfo [GymID=" + GymID;
        mapData = ", Gym Address=" + GymAddress;
        mapData = ", Gym Area=" + GymArea;
        mapData = ", Longitude=" + Longitude;
        mapData = ", Latitude=" + Latitude +"]";
        return mapData;
    }
}