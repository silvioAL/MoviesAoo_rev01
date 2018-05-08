package com.example.silvio.moviesaoo.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import com.example.silvio.moviesaoo.MainApplication;
import com.example.silvio.moviesaoo.R;
import com.example.silvio.moviesaoo.adapter.MoviesListAdapter;
import com.example.silvio.moviesaoo.data.entity.MovieData;
import com.example.silvio.moviesaoo.data.local.AppContract;
import com.example.silvio.moviesaoo.databinding.ContentActivityHomeBinding;
import com.example.silvio.moviesaoo.interfaces.ContextInteraction;
import com.example.silvio.moviesaoo.interfaces.HomeInteraction;
import com.example.silvio.moviesaoo.viewmodel.HomeViewModel;

import java.util.List;

import javax.inject.Inject;

public class HomeActivity extends GenericActivity implements ContextInteraction, HomeInteraction {

    @Inject
    HomeViewModel homeViewModel;
    ContentActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.content_activity_home);
        injectDependencies();
        binding.setHomeViewModel(homeViewModel);
        homeViewModel.setNotification(this);
        homeViewModel.setStringInteraction(this);
        homeViewModel.setInteraction(this);
        homeViewModel.setHomeInteraction(this);
        homeViewModel.getMoviesByPopularity();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            fetchListBy(AppContract.SAVED_CRITERIA);
        } else if (id == R.id.popular) {
            fetchListBy(AppContract.POPULAR_CRITERIA);
        } else if (id == R.id.top_rated) {
            fetchListBy(AppContract.RATING_CRITERIA);
        }

        return super.onOptionsItemSelected(item);
    }

    private void injectDependencies() {
        MainApplication.getAppComponent().inject(this);
    }

    @Override
    public Context getContext() {
        return getBaseContext();
    }

    @Override
    public void fetchList(List<MovieData> movies) {
        binding.rvMovies.setAdapter(new MoviesListAdapter(getBaseContext(), movies));
        binding.rvMovies.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }

    @Override
    public void fetchListBy(String criteria) {
        if (criteria.equals(AppContract.RATING_CRITERIA)) {
            homeViewModel.getMoviesByRating();
        } else if (criteria.equals(AppContract.COLUMN_POPULARITY)) {
            homeViewModel.getMoviesByPopularity();
        } else if (criteria.equals(AppContract.SAVED_CRITERIA)) {
            homeViewModel.getFavorites();
        }
    }
}
