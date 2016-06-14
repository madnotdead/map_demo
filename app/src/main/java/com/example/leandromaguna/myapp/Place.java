package com.example.leandromaguna.myapp;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.UUID;

/**
 * Created by madnotdead on 13/06/16.
 */
public class Place {

    public Place(){
        mId = UUID.randomUUID();
    }
    public String getTitle() {
        return mTitle;
    }

    public LatLng getLocation() {
        return mLocation;
    }

    public void setLocation(LatLng location) {
        mLocation = location;
    }

    private LatLng mLocation;

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getAdress() {
        return mAdress;
    }

    public void setAdress(String adress) {
        mAdress = adress;
    }

    private String mTitle;
    private String mAdress;

    public UUID getId() {
        return mId;
    }

    private UUID mId;

}
