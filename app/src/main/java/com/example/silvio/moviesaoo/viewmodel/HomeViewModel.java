package com.example.silvio.moviesaoo.viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.silvio.moviesaoo.MainApplication;
import com.example.silvio.moviesaoo.data.entity.MovieData;
import com.example.silvio.moviesaoo.data.local.AppContract;
import com.example.silvio.moviesaoo.data.repo.MoviesRepository;
import com.example.silvio.moviesaoo.service.AccountServices;
import com.example.silvio.moviesaoo.util.PreferenceStorageUtil;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by silvio on 24/12/2017.
 */

public class HomeViewModel extends ViewModel {

    public MutableLiveData<List<MovieData>> DTOList = new MutableLiveData<>();
    AccountServices services;
    private MoviesRepository moviesRepository;
    private Context appContext;


    @Inject
    public HomeViewModel(AccountServices services, MoviesRepository moviesRepository) {
        this.services = services;
        this.moviesRepository = moviesRepository;
        appContext = MainApplication.get().getApplicationContext();
    }

    public void triggerLoading(String criteria) {
        saveLastSelectedCriteria(criteria);

        switch (criteria) {

            case AppContract.RATING_CRITERIA:
                getMoviesByRating();
                break;

            case AppContract.POPULAR_CRITERIA:
                getMoviesByPopularity();
                break;
            case AppContract.SAVED_CRITERIA:
                getFavorites();
                break;

            default:
                getMoviesByRating();

        }

    }

    private void saveLastSelectedCriteria(String criteria) {
        PreferenceStorageUtil storageUtil = new PreferenceStorageUtil(appContext);
        storageUtil.saveFilteringMode(criteria);
    }

    public boolean checkIfIsOnline() {

        ConnectivityManager cm =
                (ConnectivityManager) appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();

    }

    protected MutableLiveData<List<MovieData>> getMoviesByRating() {

        DTOList = moviesRepository.getMoviesByRating();

        return DTOList;
    }

    protected MutableLiveData<List<MovieData>> getMoviesByPopularity() {

        DTOList = moviesRepository.getMoviesByPopularity();

        return DTOList;
    }

    @SuppressLint("CheckResult")
    public MutableLiveData<List<MovieData>> getFavorites() {

        DTOList = moviesRepository.getFavorites(appContext);

        return DTOList;

    }

    public LiveData<List<MovieData>> getObservableListOfMovies() {
        return DTOList;
    }
}
