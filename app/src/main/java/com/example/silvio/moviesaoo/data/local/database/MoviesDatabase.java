package com.example.silvio.moviesaoo.data.local.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.example.silvio.moviesaoo.data.entity.MovieData;
import com.example.silvio.moviesaoo.data.local.dao.DAOService;
import com.example.silvio.moviesaoo.util.RoomTypeConverter;

/**
 * Created by silvioallgayertrindade on 30/01/2018.
 */

@Database(entities = {MovieData.class}, version = 1, exportSchema = false)
@TypeConverters({RoomTypeConverter.class})
public abstract class MoviesDatabase extends RoomDatabase {

    public abstract DAOService daoService();

}
