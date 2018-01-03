package com.example.silvio.moviesaoo.interfaces;

import com.example.silvio.moviesaoo.model.MovieData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by silvio on 25/12/2017.
 */

public interface MoviesListInteraction {
    ArrayList<MovieData> getMoviesList();
    void fetchList(List<MovieData> updated);
}
