package alex.com.bitcoinmanager;

import android.app.Application;

import com.facebook.stetho.Stetho;

import timber.log.Timber;

/**
 * Created by Alex on 11/3/2017.
 */

public class BitcoinManagerApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

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
}
