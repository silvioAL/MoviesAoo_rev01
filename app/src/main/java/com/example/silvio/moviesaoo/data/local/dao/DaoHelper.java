package com.example.silvio.moviesaoo.data.local.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.silvio.moviesaoo.data.local.AppContract;

/**
 * Created by silvioallgayertrindade on 28/03/2018.
 */

public class DaoHelper extends SQLiteOpenHelper {

    public DaoHelper(Context context) {
        super(context, AppContract.DATABASE_NAME, null, AppContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_NEW_TABLE = "CREATE TABLE "
                + AppContract.TABLE_NAME + " (" +
                AppContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AppContract.COLUMN_MOVIE_ID + " TEXT, " +
                AppContract.COLUMN_ADULT + " TEXT, " +
                AppContract.COLUMN_BACKDROP_PATH + " TEXT, " +
                AppContract.COLUMN_GENRE_IDS + " TEXT, " +
                AppContract.COLUMN_ORIGINAL_LENGUAGE + " TEXT, " +
                AppContract.COLUMN_ORIGINAL_TITLE + " TEXT, " +
                AppContract.COLUMN_OVERVIEW + " TEXT, " +
                AppContract.COLUMN_RELEASE_DATE + " TEXT, " +
                AppContract.COLUMN_POSTER_PATH + " TEXT, " +
                AppContract.COLUMN_POPULARITY + " TEXT, " +
                AppContract.COLUMN_TILTE + " TEXT NOT NULL, " +
                AppContract.COLUMN_VIDEO + " TEXT, " +
                AppContract.COLUMN_VOTE_AVERAGE + " TEXT, " +
                AppContract.COLUMN_VOTE_COUNT + " TEXT);";

        db.execSQL(SQL_CREATE_NEW_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + AppContract.DATABASE_NAME);
    }
}
