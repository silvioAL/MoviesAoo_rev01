package com.example.silvio.moviesaoo.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;

import com.example.silvio.moviesaoo.MainApplication;
import com.example.silvio.moviesaoo.R;
import com.example.silvio.moviesaoo.adapter.ReviewsAdapter;
import com.example.silvio.moviesaoo.adapter.TrailersAdapter;
import com.example.silvio.moviesaoo.data.entity.MovieData;
import com.example.silvio.moviesaoo.data.model.ResultsResponseModel;
import com.example.silvio.moviesaoo.databinding.ActivityMovieDetailsBinding;
import com.example.silvio.moviesaoo.interfaces.ContextInteraction;
import com.example.silvio.moviesaoo.interfaces.MoviesDetailsInteraction;
import com.example.silvio.moviesaoo.viewmodel.MovieDetailsViewModel;

import java.util.List;

import javax.inject.Inject;

public class MovieDetailsActivity extends GenericActivity implements ContextInteraction, MoviesDetailsInteraction {

    @Inject
    MovieDetailsViewModel movieDetailsViewModel;
    ActivityMovieDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);
        injectDependencies();
        MovieData movie = getIntent().getParcelableExtra("movie");
        binding.setMovie(movieDetailsViewModel);
        movieDetailsViewModel.setStringInteraction(this);
        movieDetailsViewModel.fetchField(movie);
        movieDetailsViewModel.setNotification(this);
        movieDetailsViewModel.setContextInteraction(this);
        movieDetailsViewModel.checkForTrailers();
        movieDetailsViewModel.setMoviesDetailsInteraction(this);
        movieDetailsViewModel.checkForRatings();
        movieDetailsViewModel.setupFabStatement();

    }

    private void injectDependencies() {
        MainApplication.getAppComponent().inject(this);
    }

    @Override
    public Context getContext() {
        return getBaseContext();
    }


    @Override
    public void fetchTrailersList(List<String> moviesUrls) {
        TrailersAdapter adapter = new TrailersAdapter(getBaseContext(), moviesUrls);
        binding.rvTrailers.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvTrailers.setAdapter(adapter);
    }

    @Override
    public void changeFabColor(boolean marked) {

        if (marked) {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_star_black_24dp));
        } else {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_blue_star));
        }
    }

    @Override
    public void fetchReviewsList(List<ResultsResponseModel> resultsResponseModels) {
        ReviewsAdapter adapter = new ReviewsAdapter(getBaseContext(), resultsResponseModels);
        binding.rvRatings.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        binding.rvRatings.setAdapter(adapter);
    }
}
