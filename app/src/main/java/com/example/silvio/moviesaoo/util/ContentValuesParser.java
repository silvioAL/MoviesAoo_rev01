package com.example.silvio.moviesaoo.util;

import android.content.ContentValues;

import com.example.silvio.moviesaoo.data.entity.MovieData;
import com.example.silvio.moviesaoo.data.local.AppContract;
import com.google.gson.Gson;

/**
 * Created by silvioallgayertrindade on 28/03/2018.
 */

public class ContentValuesParser {

    public static ContentValues convertMovieDetailsToDBValues(MovieData movieData){


        Gson gson = new Gson();

        ContentValues values = new ContentValues();
        values.put(AppContract.COLUMN_MOVIE_ID, movieData.getId());
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

}
