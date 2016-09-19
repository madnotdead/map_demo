package com.example.leandromaguna.myapp;

/**
 * Created by thecocacolauser on 6/14/16.
 */

import dagger.ObjectGraph;
import android.app.Application;

import java.util.Arrays;
import java.util.List;

public class BaseApplication extends Application {

    private ObjectGraph applicationGraph;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationGraph = ObjectGraph.create(getModules().toArray());
    }


    /**
     * A list of modules to use for the application graph. Subclasses can override this method to
     * provide additional modules provided they call {@code super.getModules()}.
     */
    protected List<Object> getModules() {
        return Arrays.<Object>asList(/*new AndroidModule(this),*/new MapModule(this));
    }

    ObjectGraph getApplicationGraph() {
        return applicationGraph;
    }
}
