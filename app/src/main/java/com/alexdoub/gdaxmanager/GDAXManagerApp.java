package com.alexdoub.gdaxmanager;

import android.app.Application;

import com.facebook.stetho.Stetho;

import timber.log.Timber;

/**
 * Created by Alex on 11/3/2017.
 */

public class GDAXManagerApp extends Application {

    private static GDAXManagerApp _instance;

    @Override
    public void onCreate() {
        super.onCreate();

        _instance = this;

        Stetho.initializeWithDefaults(this);
        Timber.plant(new Timber.DebugTree());
    }

    public static GDAXManagerApp getInstance() {
        return _instance;
    }
}
