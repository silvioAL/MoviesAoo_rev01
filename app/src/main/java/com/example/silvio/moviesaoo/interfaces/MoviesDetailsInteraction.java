package com.example.silvio.moviesaoo.interfaces;

import com.example.silvio.moviesaoo.data.model.ResultsResponseModel;

import java.util.List;

/**
 * Created by silvioallgayertrindade on 19/03/2018.
 */

public interface MoviesDetailsInteraction {
    void fetchTrailersList(List<String> moviesUrls);

    void changeFabColor(boolean marked);
    void fetchReviewsList(List<ResultsResponseModel> resultsResponseModels);

}
