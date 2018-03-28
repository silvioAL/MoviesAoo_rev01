package com.example.silvio.moviesaoo.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by silvio on 23/12/2017.
 */

public class MovieGenre {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;

    public MovieGenre(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
