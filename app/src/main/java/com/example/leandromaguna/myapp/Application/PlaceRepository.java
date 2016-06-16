package com.example.leandromaguna.myapp.Application;

import com.example.leandromaguna.myapp.Model.Place;
import com.google.android.gms.maps.model.LatLng;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import dagger.Module;

/**
 * Created by thecocacolauser on 6/14/16.
 */

public class PlaceRepository implements IPlaceRepository {

    Dao<Place,?> placeDao;

    public PlaceRepository(DatabaseHelper databaseHelper){

        try{
            placeDao = databaseHelper.getDao(Place.class);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @Override
    public void Save(Place place) {
        try {
            placeDao.createOrUpdate(place);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Place> GetAllPlaces() {
        try {
            return placeDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


}
