package com.example.leandromaguna.myapp.Model;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

/**
 * Created by madnotdead on 13/06/16.
 */
@DatabaseTable(tableName = "Place")
public class Place {

    @DatabaseField
    private UUID mId;

    @DatabaseField
    private String mTitle;

    @DatabaseField
    private String mAdress;

    @DatabaseField
    private LatLng mLocation;

    @DatabaseField
    private double lat;

    @DatabaseField
    private double lng;

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    @DatabaseField
    private boolean isPublic;

    public Place(){
        mId = UUID.randomUUID();
    }

    public String getTitle() {
        return mTitle;
    }

    public LatLng getLocation() {
        return mLocation;
    }

    public void setLocatiwon(LatLng location) {
        mLocation = location;

        //TODO: fix assignment
        lat = mLocation.latitude;
        lng = mLocation.longitude;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getAdress() {
        return mAdress;
    }

    public void setAdress(String adress) {
        mAdress = adress;
    }


    public UUID getId() {
        return mId;
    }

}
