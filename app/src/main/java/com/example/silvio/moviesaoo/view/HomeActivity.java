package com.example.silvio.moviesaoo.view;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import com.example.silvio.moviesaoo.MainApplication;
import com.example.silvio.moviesaoo.R;
import com.example.silvio.moviesaoo.adapter.MoviesListAdapter;
import com.example.silvio.moviesaoo.data.local.AppContract;
import com.example.silvio.moviesaoo.databinding.ContentActivityHomeBinding;
import com.example.silvio.moviesaoo.interfaces.HomeInteractions;
import com.example.silvio.moviesaoo.viewmodel.HomeViewModel;
import com.example.silvio.moviesaoo.viewmodel.ViewModelFactory;

import javax.inject.Inject;

public class HomeActivity extends GenericActivity implements LifecycleOwner, HomeInteractions {

    @Inject
    ViewModelFactory viewModelFactory;
    HomeViewModel homeViewModel;
    ContentActivityHomeBinding binding;
    MoviesListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.content_activity_home);
        injectDependencies();

        homeViewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel.class);
        if (savedInstanceState == null) {
            homeViewModel.triggerLoading("");
        }

        adapter = new MoviesListAdapter();
        binding.rvMovies.setAdapter(adapter);
        binding.rvMovies.setHasFixedSize(true);
        binding.rvMovies.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        isLoadingContent();
        observeViewModel(homeViewModel);

    }


    private void observeViewModel(HomeViewModel homeViewModel) {

        homeViewModel.getObservableListOfMovies().observe(this, movieData -> {
            if (homeViewModel.checkIfIsOnline()) {
                if (!movieData.isEmpty()) {
                    finishedLoading();
                    adapter.updateList(movieData);
                }
            } else {
                showError(getString(R.string.offline_message));
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            homeViewModel.triggerLoading(AppContract.SAVED_CRITERIA);
        } else if (id == R.id.popular) {
            homeViewModel.triggerLoading(AppContract.POPULAR_CRITERIA);
        } else if (id == R.id.top_rated) {
            homeViewModel.triggerLoading(AppContract.RATING_CRITERIA);
        }

        return super.onOptionsItemSelected(item);
    }

    private void injectDependencies() {
        MainApplication.getAppComponent().inject(this);
    }

    @Override
    public void isLoadingContent() {
        this.showLoading();
    }

    @Override
    public void finishedLoading() {
        this.hideLoading();
    }

    @Override
    public void showError(String error) {
        this.openAlertDialog(error);
    }
}
