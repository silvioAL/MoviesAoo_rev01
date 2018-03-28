package com.example.silvio.moviesaoo;

import android.app.Application;

import com.example.silvio.moviesaoo.inject.components.AppComponent;
import com.example.silvio.moviesaoo.inject.components.DaggerAppComponent;
import com.example.silvio.moviesaoo.inject.module.AppModule;
import com.example.silvio.moviesaoo.inject.module.ContextModule;
import com.example.silvio.moviesaoo.inject.module.NetworkModule;

/**
 * Created by silvio on 13/12/2017.
 */


public class MainApplication extends android.app.Application {

    private static AppComponent component;
    private static Application instance;

    public static AppComponent getAppComponent() {
        return component;
    }

    /**
     * Returns a instance of this application.
     *
     * @return
     */
    public static Application get() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initDagger();
        if (instance == null) {
            instance = this;
        }
    }

    /**
     * Start dagger component
     */
    private void initDagger() {
        if (component == null) {
            component = DaggerAppComponent.builder()
                    .networkModule(new NetworkModule(getResources().getString(R.string.API_BASE_URL)))
                    .appModule(new AppModule(MainApplication.this))
                    .contextModule(new ContextModule(getApplicationContext()))
                    .build();


        }
    }
}


