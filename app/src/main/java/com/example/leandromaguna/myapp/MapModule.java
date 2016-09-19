package com.example.leandromaguna.myapp;

import android.content.Context;
import android.location.LocationManager;

import com.example.leandromaguna.myapp.Application.DatabaseHelper;
import com.example.leandromaguna.myapp.Application.IPlaceRepository;
import com.example.leandromaguna.myapp.Application.PlaceRepository;
import com.example.leandromaguna.myapp.Application.PlaceService;
import com.example.leandromaguna.myapp.BaseApplication;
import com.example.leandromaguna.myapp.ForApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by thecocacolauser on 6/15/16.
 */
@Module
public class MapModule {

    private BaseApplication mApplication;

    public MapModule(BaseApplication application){
        mApplication = application;
    }

    @Provides
    @Singleton
    @ForApplication
    Context provideApplicationContext() {
        return mApplication;
    }

    @Provides @Singleton
    LocationManager provideLocationManager() {
        return (LocationManager) mApplication.getSystemService(LOCATION_SERVICE);
    }

    @Provides @Singleton
    DatabaseHelper provideDatabaseHelper(){
        return new DatabaseHelper(mApplication);
    }

    @Provides @Singleton
    IPlaceRepository providePlaceRepository(){
        return new PlaceRepository(provideDatabaseHelper());
    }
    @Provides @Singleton
    PlaceService providePlaceService(){
        return new PlaceService();
    }
}
