package com.example.leandromaguna.myapp.Application;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

import dagger.Module;

/**
 * Created by thecocacolauser on 6/14/16.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private final String TAG = DatabaseHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "places.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        Log.i(TAG,"onCreate()");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
