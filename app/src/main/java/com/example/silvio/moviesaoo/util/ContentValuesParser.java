package com.example.silvio.moviesaoo.util;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.silvio.moviesaoo.data.entity.MovieData;
import com.example.silvio.moviesaoo.data.local.AppContract;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by silvioallgayertrindade on 28/03/2018.
 */

public class ContentValuesParser {

    public static ContentValues convertMovieDetailsToDBValues(MovieData movieData){

        Gson gson = new Gson();

        ContentValues values = new ContentValues();
        values.put(AppContract.COLUMN_MOVIE_ID, movieData.getMovieId());
        values.put(AppContract.COLUMN_ADULT, movieData.isAdult());
        values.put(AppContract.COLUMN_BACKDROP_PATH, movieData.getBackdrop_path());
        values.put(AppContract.COLUMN_GENRE_IDS, gson.toJson(movieData.getGenre_ids()));
        values.put(AppContract.COLUMN_ORIGINAL_LENGUAGE, movieData.getOriginal_language());
        values.put(AppContract.COLUMN_ORIGINAL_TITLE, movieData.getOriginal_title());
        values.put(AppContract.COLUMN_OVERVIEW, movieData.getOverview());
        values.put(AppContract.COLUMN_RELEASE_DATE, movieData.getRelease_date());
        values.put(AppContract.COLUMN_POSTER_PATH, movieData.getPoster_path());
        values.put(AppContract.COLUMN_POPULARITY, movieData.getPopularity());
        values.put(AppContract.COLUMN_TILTE, movieData.getTitle());
        values.put(AppContract.COLUMN_VIDEO, movieData.getVideo());
        values.put(AppContract.COLUMN_VOTE_AVERAGE, movieData.getVote_average());
        values.put(AppContract.COLUMN_VOTE_COUNT, movieData.getVote_count());

        return values;

    }

    public static ArrayList<String> fromString(String value) {
        Type listType = new TypeToken<ArrayList<String>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    public static MovieData extractMovieFromCursor(Cursor cursor) {

        int movie_DB_ID_Index = cursor.getColumnIndex(AppContract._ID);
        int movie_ID_Index = cursor.getColumnIndex(AppContract.COLUMN_MOVIE_ID);
        int movie_ADULT_Index = cursor.getColumnIndex(AppContract.COLUMN_ADULT);
        int movie_BACKDROP_PATH_Index = cursor.getColumnIndex(AppContract.COLUMN_BACKDROP_PATH);
        int movie_GENRE_IDS_Index = cursor.getColumnIndex(AppContract.COLUMN_GENRE_IDS);
        int movie_ORIGINAL_LENGUAGE_Index = cursor.getColumnIndex(AppContract.COLUMN_ORIGINAL_LENGUAGE);
        int movie_ORIGINAL_TITLE_Index = cursor.getColumnIndex(AppContract.COLUMN_ORIGINAL_TITLE);
        int movie_OVERVIEW_Index = cursor.getColumnIndex(AppContract.COLUMN_OVERVIEW);
        int movie_RELEASE_DATE_Index = cursor.getColumnIndex(AppContract.COLUMN_RELEASE_DATE);
        int movie_POSTER_PATH_Index = cursor.getColumnIndex(AppContract.COLUMN_POSTER_PATH);
        int movie_POPULARITY_Index = cursor.getColumnIndex(AppContract.COLUMN_POPULARITY);
        int movie_TITLE_Index = cursor.getColumnIndex(AppContract.COLUMN_ORIGINAL_TITLE);
        int movie_VIDEO_Index = cursor.getColumnIndex(AppContract.COLUMN_VIDEO);
        int movie_VOTE_AVERAGE_Index = cursor.getColumnIndex(AppContract.COLUMN_VOTE_AVERAGE);
        int movie_VOTE_COUNT_Index = cursor.getColumnIndex(AppContract.COLUMN_VOTE_COUNT);


        MovieData movieData = new MovieData();
        movieData.setDbId(cursor.getInt(movie_DB_ID_Index));
        movieData.setMovieId(cursor.getString(movie_ID_Index));
        movieData.setAdult(cursor.getInt(movie_ADULT_Index) > 0);
        movieData.setBackdrop_path(cursor.getString(movie_BACKDROP_PATH_Index));
        movieData.setGenre_ids(fromString(cursor.getString(movie_GENRE_IDS_Index)));
        movieData.setOriginal_language(cursor.getString(movie_ORIGINAL_LENGUAGE_Index));
        movieData.setOriginal_title(cursor.getString(movie_ORIGINAL_TITLE_Index));
        movieData.setOverview(cursor.getString(movie_OVERVIEW_Index));
        movieData.setRelease_date(cursor.getString(movie_RELEASE_DATE_Index));
        movieData.setPoster_path(cursor.getString(movie_POSTER_PATH_Index));
        movieData.setPopularity(cursor.getString(movie_POPULARITY_Index));
        movieData.setTitle(cursor.getString(movie_TITLE_Index));
        movieData.setVideo(cursor.getString(movie_VIDEO_Index));
        movieData.setVote_average(cursor.getString(movie_VOTE_AVERAGE_Index));
        movieData.setVote_count(cursor.getString(movie_VOTE_COUNT_Index));

        return movieData;


    }

}
