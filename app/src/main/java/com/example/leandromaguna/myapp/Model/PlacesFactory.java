package com.example.leandromaguna.myapp.Model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by madnotdead on 13/06/16.
 */
public class PlacesFactory {

    private static PlacesFactory instance;
    private List<Place> mPlaces;

    public static PlacesFactory get(Context context){

        if(instance == null){
            instance = new PlacesFactory(context);
        }

        return instance;
    }

    private PlacesFactory(Context context){

        mPlaces = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Place place = new Place();
            place.setTitle("Places #" + i);
            place.setAdress("Adress #" + i);
            mPlaces.add(place);
        }
    }

    public List<Place> getPlaces() {
        return mPlaces;
    }

    public Place getPlace(UUID id){

        for (Place place:mPlaces) {
            if(id.equals(place.getId())){
                return place;
            }
        }

        return null;
    }

    public void savePlace(Place newPlace){

        if(newPlace!= null)
            mPlaces.add(newPlace);
    }
}
