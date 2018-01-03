package com.example.silvio.moviesaoo.util;

import com.google.gson.Gson;

/**
 * Created by silvio on 24/12/2017.
 */

public class GenericJsonParser {
    public static <T> T jsonToObject(String stringToParse, Class<T> type) {
        return new Gson().fromJson(stringToParse, type);
    }

    public static <T> String objectToJson(T objectToParse) {
        Gson gson = new Gson();
        return gson.toJson(objectToParse);
    }
}