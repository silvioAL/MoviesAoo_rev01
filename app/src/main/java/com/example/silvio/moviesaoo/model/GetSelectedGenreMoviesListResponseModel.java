package com.example.silvio.moviesaoo.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by silvio on 25/12/2017.
 */

public class GetSelectedGenreMoviesListResponseModel {

    @SerializedName("id")
    private String id;
    @SerializedName("page")
    private String page;
    @SerializedName("results")
    private List<MovieData> results;

    public GetSelectedGenreMoviesListResponseModel(String id, String page, List<MovieData> results) {
        this.id = id;
        this.page = page;
        this.results = results;
    }

    public String getId() {
        return id;
    }

    public String getPage() {
        return page;
    }

    public List<MovieData> getResults() {
        return results;
    }
}
