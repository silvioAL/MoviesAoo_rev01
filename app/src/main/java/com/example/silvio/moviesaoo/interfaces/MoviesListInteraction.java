package com.example.silvio.moviesaoo.interfaces;

import android.content.Intent;

import com.example.silvio.moviesaoo.data.entity.MovieData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by silvio on 25/12/2017.
 */

public interface MoviesListInteraction {
    ArrayList<MovieData> getMoviesList(Intent intent);
    void fetchList(List<MovieData> updated);
    Intent getExtras();
}
