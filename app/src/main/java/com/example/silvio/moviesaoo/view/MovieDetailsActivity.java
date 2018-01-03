package com.example.silvio.moviesaoo.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.example.silvio.moviesaoo.MainApplication;
import com.example.silvio.moviesaoo.R;
import com.example.silvio.moviesaoo.databinding.ActivityMovieDetailsBinding;
import com.example.silvio.moviesaoo.model.MovieData;
import com.example.silvio.moviesaoo.viewmodel.MovieDetailsViewModel;

import javax.inject.Inject;

public class MovieDetailsActivity extends GenericActivity {

    @Inject
    MovieDetailsViewModel movieDetailsViewModel;
    ActivityMovieDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);
        injectDependencies();
        MovieData movie = (MovieData) getIntent().getParcelableExtra("movie");
        binding.setMovie(movieDetailsViewModel);
        movieDetailsViewModel.setStringInteraction(this);
        movieDetailsViewModel.fetchField(movie);


    }

    private void injectDependencies() {
        MainApplication.getAppComponent().inject(this);
    }

}
