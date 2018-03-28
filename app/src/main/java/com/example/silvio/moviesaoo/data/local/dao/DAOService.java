package com.example.silvio.moviesaoo.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import com.example.silvio.moviesaoo.data.entity.MovieData;
import com.example.silvio.moviesaoo.data.local.AppContract;

/**
 * Created by silvioallgayertrindade on 30/01/2018.
 */

@Dao
public interface DAOService {

    @Query("SELECT * FROM " + AppContract.TABLE_NAME)
    Cursor getAllMovies();

    @Query("SELECT * FROM " + AppContract.TABLE_NAME + " WHERE isFavorite=:isFavorite")
    Cursor getFavorites(boolean isFavorite);

    @Update
    int updateMovie(MovieData movieData);

    /**
     * JUST USING A THIS @QUERY ANNOTATION BECAUSE A CONTENT PROVIDER IS REQUIRED IN THIS PROJECT
     * , SO GOOGLE'S DELETE method does not support a ContentValues @param
     */

    @Query("DELETE FROM " + AppContract.TABLE_NAME + " WHERE movie_id=:movie_id")
    int deleteMovie(long movie_id);

    @Insert(onConflict = OnConflictStrategy.FAIL)
    long putMovie(MovieData movieData);
}
