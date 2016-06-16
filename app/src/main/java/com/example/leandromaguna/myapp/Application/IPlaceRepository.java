package com.example.leandromaguna.myapp.Application;

import com.example.leandromaguna.myapp.Model.Place;

import java.util.List;

import dagger.Component;

import javax.inject.Singleton;

/**
 * Created by thecocacolauser on 6/14/16.
 */

public interface IPlaceRepository {

    void Save(Place place);
    List<Place> GetAllPlaces();
}
