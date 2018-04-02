package com.example.silvio.moviesaoo.util;

import com.example.silvio.moviesaoo.data.local.AppContract;

/**
 * Created by silvioallgayertrindade on 29/03/2018.
 */

public class ProjectionHelper {

    public static String[] getMovieDetailsProjection() {

        String[] projection = {
                AppContract.COLUMN_MOVIE_ID
                , AppContract.COLUMN_ADULT
                , AppContract.COLUMN_BACKDROP_PATH
                , AppContract.COLUMN_GENRE_IDS
                , AppContract.COLUMN_ORIGINAL_LENGUAGE
                , AppContract.COLUMN_ORIGINAL_TITLE
                , AppContract.COLUMN_OVERVIEW
                , AppContract.COLUMN_RELEASE_DATE
                , AppContract.COLUMN_POSTER_PATH
                , AppContract.COLUMN_POPULARITY
                , AppContract.COLUMN_TILTE
                , AppContract.COLUMN_VIDEO
                , AppContract.COLUMN_VOTE_AVERAGE
                , AppContract.COLUMN_VOTE_COUNT
        };

        return projection;

    }

}
