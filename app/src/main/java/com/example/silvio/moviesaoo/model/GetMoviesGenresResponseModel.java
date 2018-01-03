package com.example.silvio.moviesaoo.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by silvio on 24/12/2017.
 */

public class GetMoviesGenresResponseModel {

    @SerializedName("genres")
    private List<MovieGenre> genreList;

    public GetMoviesGenresResponseModel(List<MovieGenre> genreList) {
        this.genreList = genreList;
    }

    public List<MovieGenre> getGenreList() {
        return genreList;
    }
}
