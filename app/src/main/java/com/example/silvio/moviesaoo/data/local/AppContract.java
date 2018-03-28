package com.example.silvio.moviesaoo.data.local;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by silvioallgayertrindade on 30/01/2018.
 */

public class AppContract {

    public static final String DATABASE_NAME = "MOVIES_AOO__APP.db";

    //DB_INFO
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "MOVIES_TABLE";

    //DATA TABLE CONSTANTS
    public static final String _ID = BaseColumns._ID;
    public static final String COLUMN_TILTE = "TITLE";
    public static final String COLUMN_ORIGINAL_LENGUAGE = "ORIGINAL_LENG";
    public static final String COLUMN_ORIGINAL_TITLE = "ORIGINAL_TITLE";
    public static final String COLUMN_OVERVIEW = "OVERVIEW";
    public static final String COLUMN_RELEASE_DATE = "RELEASE_DATE";
    public static final String COLUMN_POPULARITY = "POPULARITY";
    public static final String COLUMN_VOTE_AVERAGE = "VOTE_AVERAGE";
    public static final String COLUMN_VOTE_COUNT = "VOTE_COUNT";
    public static final String COLUMN_MOVIE_IMG_URL = "MOVIE_IMG_URL";
    public static final String CONTENT_AUTHORITY = "com.example.silvio.moviesaoo";

    //URI MATCHERS QUERY CODE TYPE
    public static final Integer ALL_ITEMS_QUERY_TYPE = 7236;
    public static final Integer SOME_ITEMS_QUERY_TYPE = 7236;

    //CONTENT CONSTANTS
    public static final String MOVIES_APPREF = "MOVIES";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_ITEM = "MOVIES";
    public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ITEM);
    public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE +
            "/" + CONTENT_AUTHORITY + "/" + PATH_ITEM;
    public static final int MAIN_LIST_LOADER_ID = 7262;
}
