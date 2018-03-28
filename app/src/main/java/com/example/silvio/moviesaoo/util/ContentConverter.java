package com.example.silvio.moviesaoo.util;

import android.content.ContentValues;

import com.example.silvio.moviesaoo.data.entity.MovieData;
import com.example.silvio.moviesaoo.data.local.AppContract;

/**
 * Created by silvioallgayertrindade on 06/02/2018.
 */

public class ContentConverter {

    public static MovieData convertContentValueToMovieData(ContentValues contentValues) {

        MovieData movieData = new MovieData();

        String title = contentValues.getAsString(AppContract.COLUMN_TILTE);
        if (title != null) {
            movieData.setOriginal_title(contentValues.getAsString(AppContract.COLUMN_ORIGINAL_TITLE));
            movieData.setPoster_path(contentValues.getAsString(AppContract.COLUMN_MOVIE_IMG_URL));
            movieData.setOriginal_language(contentValues.getAsString(AppContract.COLUMN_ORIGINAL_LENGUAGE));
            movieData.setOverview(contentValues.getAsString(AppContract.COLUMN_OVERVIEW));
            movieData.setPopularity(contentValues.getAsString(AppContract.COLUMN_POPULARITY));
            movieData.setRelease_date(contentValues.getAsString(AppContract.COLUMN_RELEASE_DATE));
            movieData.setVote_average(contentValues.getAsString(AppContract.COLUMN_VOTE_AVERAGE));
            movieData.setVote_count(contentValues.getAsString(AppContract.COLUMN_VOTE_COUNT));
        }

        return movieData;

    }

}
