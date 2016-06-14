package com.example.leandromaguna.myapp.Application;

import com.example.leandromaguna.myapp.Model.Place;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by thecocacolauser on 6/14/16.
 */
public class PlaceService {

    IPlaceRepository _placeRespository;

    public void savePlace(String name, String description, boolean isPublic, LatLng position){

        Place mPlace = new Place();

        mPlace.setTitle(name);
        mPlace.setAdress(description);
        mPlace.setPublic(isPublic);
        mPlace.setLocatiwon(position);

        _placeRespository.Save(mPlace);
    }
}
