package com.example.silvio.moviesaoo.inject.module;

import android.content.Context;

import com.example.silvio.moviesaoo.data.local.dao.DaoHelper;
import com.example.silvio.moviesaoo.inject.scopes.MoviesAppScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by silvioallgayertrindade on 05/02/2018.
 */

@Module(includes = ContextModule.class)
public class DataBaseModule {

    @Provides
    @MoviesAppScope
    DaoHelper providesSQLiteHelper(Context mContext) {
      DaoHelper helper =  new DaoHelper(mContext);
      return helper;
    }

}
