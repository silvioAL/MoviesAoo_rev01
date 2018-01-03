package com.example.silvio.moviesaoo.interfaces;

import com.example.silvio.moviesaoo.model.GetMoviesGenresResponseModel;

/**
 * Created by silvio on 24/12/2017.
 */

public interface HomeInteraction {

    void fetchGenres(GetMoviesGenresResponseModel genres);
    String getGenreSelection();
}
