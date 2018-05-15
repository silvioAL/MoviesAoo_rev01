package com.example.silvio.moviesaoo.data.model;

import com.example.silvio.moviesaoo.data.entity.MovieData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by silvio on 25/12/2017.
 */

public class SearchMovieResponseModel {

    @SerializedName("page")
    private String page;
    @SerializedName("total_results")
    private String total_results;
    @SerializedName("total_pages")
    private String total_pages;
    @SerializedName("results")
    private List<MovieData> movieDataList;

    public SearchMovieResponseModel(String page, String total_results, String total_pages, List<MovieData> movieDataList) {
        this.page = page;
        this.total_results = total_results;
        this.total_pages = total_pages;
        this.movieDataList = movieDataList;
    }

    public List<MovieData> getMovieDataList() {
        return movieDataList;
    }
}
