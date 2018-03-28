package com.example.silvio.moviesaoo.inject.components;

import com.example.silvio.moviesaoo.inject.module.AppModule;
import com.example.silvio.moviesaoo.inject.module.ContextModule;
import com.example.silvio.moviesaoo.inject.module.DataBaseModule;
import com.example.silvio.moviesaoo.inject.module.NetworkModule;
import com.example.silvio.moviesaoo.inject.scopes.MoviesAppScope;
import com.example.silvio.moviesaoo.util.FontManager;
import com.example.silvio.moviesaoo.view.HomeActivity;
import com.example.silvio.moviesaoo.view.LoginActivity;
import com.example.silvio.moviesaoo.view.MovieDetailsActivity;
import com.example.silvio.moviesaoo.view.MoviesListActivity;
import com.example.silvio.moviesaoo.view.SplashScreenActivity;

import dagger.Component;

/**
 * Created by silvio on 13/12/2017.
 */
@MoviesAppScope
@Component(modules = {AppModule.class, NetworkModule.class, DataBaseModule.class, ContextModule.class})
public interface AppComponent {

    FontManager getFontManager();
    void inject(LoginActivity loginActivity);
    void inject(SplashScreenActivity splashScreenActivity);
    void inject(HomeActivity homeActivity);
    void inject(MoviesListActivity moviesListActivity);
    void inject(MovieDetailsActivity movieDetailsActivity);

}

