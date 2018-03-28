package com.example.silvio.moviesaoo.inject.module;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.silvio.moviesaoo.data.local.AppContract;
import com.example.silvio.moviesaoo.data.local.dao.DAOService;
import com.example.silvio.moviesaoo.data.local.database.MoviesDatabase;
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
    MoviesDatabase providesDataBase(Context mContext) {
        RoomDatabase.Builder<MoviesDatabase> builder = Room.databaseBuilder(mContext.getApplicationContext(),
                MoviesDatabase.class,
                AppContract.TABLE_NAME);
        return builder.build();
    }

    @Provides
    @MoviesAppScope
    DAOService providesDAO(MoviesDatabase moviesDatabase) {
        return moviesDatabase.daoService();
    }
}
