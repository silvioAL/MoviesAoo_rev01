package com.example.silvio.moviesaoo.inject.module;

import android.app.Application;
import android.content.Context;

import com.example.silvio.moviesaoo.MainApplication;
import com.example.silvio.moviesaoo.util.FontManager;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by silvio on 13/12/2017.
 */

@Module
public class AppModule {

    private final MainApplication application;

    public AppModule(MainApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    public FontManager provideFontManager(){ return new FontManager(application);}
}

