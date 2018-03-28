package com.example.silvio.moviesaoo.viewmodel;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import com.example.silvio.moviesaoo.data.entity.MovieData;
import com.example.silvio.moviesaoo.inject.scopes.MoviesAppScope;
import com.example.silvio.moviesaoo.interfaces.ContextInteraction;
import com.example.silvio.moviesaoo.interfaces.GenericNotification;
import com.example.silvio.moviesaoo.interfaces.GenericStringInteraction;
import com.example.silvio.moviesaoo.interfaces.MoviesListInteraction;
import com.example.silvio.moviesaoo.service.AccountServices;

import java.util.ArrayList;
import java.util.Collections;

import javax.inject.Inject;

/**
 * Created by silvio on 25/12/2017.
 */

@MoviesAppScope
public class MoviesListViewModel extends BaseObservable {

    public ObservableField<Boolean> sortByPopularity = new ObservableField<>(false);
    private AccountServices services;
    private GenericNotification notification;
    private GenericStringInteraction stringInteraction;
    private ContextInteraction contextInteraction;
    private ObservableField<String> movieKeyWord = new ObservableField<>();
    private ObservableField<String> movieGenreKeyWord = new ObservableField<>();
    private MoviesListInteraction interaction;
    private ArrayList<MovieData> movies;

    @Inject
    public MoviesListViewModel(AccountServices services) {
        this.services = services;
    }

    public void setNotification(GenericNotification notification) {
        this.notification = notification;
    }

    public void setStringInteraction(GenericStringInteraction stringInteraction) {
        this.stringInteraction = stringInteraction;
    }

    public void setInteraction(MoviesListInteraction interaction) {
        this.interaction = interaction;
    }

    public void setContextInteraction(ContextInteraction contextInteraction) {
        this.contextInteraction = contextInteraction;
    }

    public ArrayList<MovieData> sortList() {

        notification.showLoading();
        retrieveMoviesList(interaction.getExtras());
        ArrayList<MovieData> listToSort = movies;

        if (sortByPopularity.get()) {

            Collections.sort(listToSort, (movieData, secMovieData) -> {
                Double prev = Double.parseDouble(movieData.getPopularity());
                Double current = Double.parseDouble(secMovieData.getPopularity());
                return prev.compareTo(current);
            });

        } else {
            Collections.sort(listToSort, (movieData, secMovieData) -> {
                Double prev = Double.parseDouble(movieData.getVote_average());
                Double current = Double.parseDouble(secMovieData.getVote_average());
                return prev.compareTo(current);
            });
        }
        notification.hideLoading();
        return listToSort;
    }


    public void retrieveMoviesList(Intent intent) {
        movies = interaction.getMoviesList(intent);
    }

    public void setupData() {
        ArrayList<MovieData> sorted = sortList();
        interaction.fetchList(sorted);
    }

}
