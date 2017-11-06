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

        initializeStetho();
        Timber.plant(new Timber.DebugTree());
    }

    protected void initializeStetho() {

        Stetho.InitializerBuilder initializerBuilder = Stetho.newInitializerBuilder(this);
        initializerBuilder.enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this));     // Enable Chrome DevTools
        initializerBuilder.enableDumpapp(Stetho.defaultDumperPluginsProvider(this));                // Enable command line interface

        Stetho.Initializer initializer = initializerBuilder.build();
        Stetho.initialize(initializer);
//        Stetho.initializeWithDefaults(this);
    }

    public static GDAXManagerApp getInstance() {
        return _instance;
    }
}
