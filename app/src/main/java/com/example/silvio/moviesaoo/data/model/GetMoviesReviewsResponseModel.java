package com.example.silvio.moviesaoo.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by silvioallgayertrindade on 27/03/2018.
 */

public class GetMoviesReviewsResponseModel {
    @SerializedName("results")
    List<ResultsResponseModel> results;

    public GetMoviesReviewsResponseModel(List<ResultsResponseModel> results) {
        this.results = results;
    }

    public List<ResultsResponseModel> getResults() {
        return results;
    }

    public void setResults(List<ResultsResponseModel> results) {
        this.results = results;
    }
}
