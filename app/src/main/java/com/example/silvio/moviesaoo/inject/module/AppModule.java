package com.example.silvio.moviesaoo.inject.module;

import android.app.Application;

import com.example.silvio.moviesaoo.MainApplication;
import com.example.silvio.moviesaoo.inject.scopes.MoviesAppScope;
import com.example.silvio.moviesaoo.util.FontManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by silvio on 13/12/2017.
 */

@Module(includes = ContextModule.class)
public class AppModule {

    private final MainApplication application;

    public AppModule(MainApplication application) {
        this.application = application;
    }

    @Provides
    @MoviesAppScope
    public Application provideApplication() {
        return application;
    }


    @Provides
    @MoviesAppScope
    public FontManager provideFontManager(){ return new FontManager(application);}
}

