package com.example.silvio.moviesaoo.model;

/**
 * Created by silvio on 19/12/2017.
 */

public class BearerHolder {

    private static String tempKey;
    private static BearerHolder bearerHolder;

    public static BearerHolder getInstance() {
        if (bearerHolder == null)
            bearerHolder = new BearerHolder();

        return bearerHolder;
    }

    public String getTempKey() {
        return tempKey;
    }
}
