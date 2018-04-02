package com.example.silvio.moviesaoo.data.local;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.silvio.moviesaoo.data.entity.MovieData;
import com.example.silvio.moviesaoo.data.local.dao.DaoHelper;
import com.example.silvio.moviesaoo.util.ContentConverter;
import com.example.silvio.moviesaoo.util.ProjectionHelper;

import javax.inject.Inject;

import static android.R.attr.data;
import static android.R.attr.id;

/**
 * Created by silvioallgayertrindade on 30/01/2018.
 */

public class MoviesProvider extends ContentProvider {

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    DaoHelper helper;

    @Override
    public boolean onCreate() {
        helper = new DaoHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor cursor;

        SQLiteDatabase database = helper.getReadableDatabase();

        cursor = database.query(AppContract.TABLE_NAME, ProjectionHelper.getMovieDetailsProjection(), null, null, null, null, null);
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
        long mDataBaseIndicator = -1;

        assert contentValues != null;

        SQLiteDatabase database = helper.getReadableDatabase();
        mDataBaseIndicator = database.insert(AppContract.TABLE_NAME, null, contentValues);
        if (mDataBaseIndicator == -1) {
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        int deletedRows;
        SQLiteDatabase database = helper.getReadableDatabase();

        deletedRows = database.delete(AppContract.TABLE_NAME, selection, selectionArgs);

        return deletedRows;

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {

        int updatedRows;
        SQLiteDatabase database = helper.getReadableDatabase();
        updatedRows = database.update(AppContract.TABLE_NAME, contentValues, selection, selectionArgs);

        return updatedRows;
    }
}
