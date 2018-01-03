package com.example.silvio.moviesaoo.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.silvio.moviesaoo.MainApplication;
import com.example.silvio.moviesaoo.R;
import com.example.silvio.moviesaoo.databinding.ActivityHomeBinding;
import com.example.silvio.moviesaoo.interfaces.ContextInteraction;
import com.example.silvio.moviesaoo.interfaces.HomeInteraction;
import com.example.silvio.moviesaoo.model.GetMoviesGenresResponseModel;
import com.example.silvio.moviesaoo.model.MovieGenre;
import com.example.silvio.moviesaoo.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class HomeActivity extends GenericActivity implements ContextInteraction, HomeInteraction {

    @Inject
    HomeViewModel homeViewModel;
    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        injectDependencies();
        binding.setHomeViewModel(homeViewModel);
        homeViewModel.setNotification(this);
        homeViewModel.setStringInteraction(this);
        homeViewModel.setInteraction(this);
        homeViewModel.setHomeInteraction(this);
        homeViewModel.loadMovieGenres();
    }

    private void injectDependencies() {
        MainApplication.getAppComponent().inject(this);
    }

    @Override
    public Context getContext() {
        return getBaseContext();
    }

    @Override
    public void fetchGenres(GetMoviesGenresResponseModel genres) {
        List<String> genresTitles = new ArrayList<>();
        genresTitles.clear();
        genresTitles.add("");

        for (MovieGenre title : genres.getGenreList()) {
            genresTitles.add(title.getName());
        }

        ArrayAdapter<String> nAdapter = new ArrayAdapter<String>(getContext()
                , android.R.layout.simple_spinner_dropdown_item
                , genresTitles);

        nAdapter.notifyDataSetChanged();

        binding.genresSpinner.setAdapter(nAdapter);
        binding.genresSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                binding.genresSpinner.setSelection(i);
                homeViewModel.movieGenre.set(getGenreSelection());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                binding.genresSpinner.setSelection(0);
            }
        });
    }

    @Override
    public String getGenreSelection() {
        return binding.genresSpinner.getSelectedItem().toString();
    }
}
