package com.example.silvio.moviesaoo.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import com.example.silvio.moviesaoo.R;
import com.example.silvio.moviesaoo.interfaces.GenericStringInteraction;
import com.example.silvio.moviesaoo.model.MovieData;
import com.example.silvio.moviesaoo.service.AccountServices;
import com.example.silvio.moviesaoo.util.StringUtil;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by silvio on 26/12/2017.
 */

@Singleton
public class MovieDetailsViewModel extends BaseObservable{

    public ObservableField<String> title = new ObservableField<>();
    public ObservableField<String> originalLenguage = new ObservableField<>();
    public ObservableField<String> originalTitle = new ObservableField<>();
    public ObservableField<String> overview = new ObservableField<>();
    public ObservableField<String> releaseDate = new ObservableField<>();
    public ObservableField<String> popularity = new ObservableField<>();
    public ObservableField<String> voteAvarage = new ObservableField<>();
    public ObservableField<String> voteCount = new ObservableField<>();
    public ObservableField<String> movieImgUrl = new ObservableField<>();

    private AccountServices services;
    private GenericStringInteraction stringInteraction;

    @Inject
    public MovieDetailsViewModel(AccountServices services) {
        this.services = services;
    }

    public void setStringInteraction(GenericStringInteraction stringInteraction) {
        this.stringInteraction = stringInteraction;
    }

    public void fetchField(MovieData movieData){

        title.set(movieData.getTitle());
        originalLenguage.set(movieData.getOriginal_language());
        originalTitle.set(movieData.getOriginal_title());
        overview.set(movieData.getOverview());
        releaseDate.set(StringUtil.formateDateFromServerResource(movieData.getRelease_date()));
        popularity.set(movieData.getPopularity());
        voteAvarage.set(movieData.getVote_average());
        voteCount.set(movieData.getVote_count());
        movieImgUrl.set(stringInteraction.getStringFromResource(R.string.image_service_URL) + movieData.getPoster_path());

    }

}
