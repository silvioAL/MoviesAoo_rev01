package com.example.silvio.moviesaoo.data.local;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.silvio.moviesaoo.data.entity.MovieData;
import com.example.silvio.moviesaoo.data.local.dao.DAOService;
import com.example.silvio.moviesaoo.data.local.database.MoviesDatabase;
import com.example.silvio.moviesaoo.util.ContentConverter;

import javax.inject.Inject;

import static android.R.attr.id;

/**
 * Created by silvioallgayertrindade on 30/01/2018.
 */

public class MoviesProvider extends ContentProvider {

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AppContract.CONTENT_AUTHORITY, AppContract.TABLE_NAME, AppContract.ALL_ITEMS_QUERY_TYPE);
        URI_MATCHER.addURI(AppContract.CONTENT_AUTHORITY, AppContract.TABLE_NAME, AppContract.SOME_ITEMS_QUERY_TYPE);
    }

    @Inject
    DAOService helper;
    @Inject
    MoviesDatabase database;

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor cursor;
        int matchingResult = URI_MATCHER.match(uri);
        if (matchingResult == AppContract.ALL_ITEMS_QUERY_TYPE) {
            cursor = helper.getAllMovies();
        } else {
            cursor = helper.getFavorites(true);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return AppContract.CONTENT_LIST_TYPE;
    }

    @SuppressWarnings("ConstantConditions")
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        long mDataBaseID = -1;

        assert contentValues != null;
        String description = contentValues.getAsString(AppContract.COLUMN_TILTE);

        if (description != null) {
            MovieData movieData = ContentConverter.convertContentValueToMovieData(contentValues);
            mDataBaseID = helper.putMovie(movieData);
        }
        if (mDataBaseID == -1) {
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        int deletedRows;

        deletedRows = helper.deleteMovie(ContentUris.parseId(uri));

        return deletedRows;

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {

        int updatedRows;

        updatedRows = helper.updateMovie(ContentConverter.convertContentValueToMovieData(contentValues));

        return updatedRows;
    }
}
