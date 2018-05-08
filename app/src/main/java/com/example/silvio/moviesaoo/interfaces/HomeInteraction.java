package com.example.silvio.moviesaoo.interfaces;

import com.example.silvio.moviesaoo.data.entity.MovieData;

import java.util.List;

/**
 * Created by silvio on 24/12/2017.
 */

public interface HomeInteraction {

    void fetchList(List<MovieData> moviesResponseModel);

    void fetchListBy(String criteria);

}
