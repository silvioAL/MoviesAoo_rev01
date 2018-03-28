package com.example.silvio.moviesaoo.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;

import com.example.silvio.moviesaoo.MainApplication;
import com.example.silvio.moviesaoo.R;
import com.example.silvio.moviesaoo.adapter.MoviesListAdapter;
import com.example.silvio.moviesaoo.data.entity.MovieData;
import com.example.silvio.moviesaoo.databinding.ActivityMoviesListBinding;
import com.example.silvio.moviesaoo.interfaces.ContextInteraction;
import com.example.silvio.moviesaoo.interfaces.MoviesListInteraction;
import com.example.silvio.moviesaoo.viewmodel.MoviesListViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MoviesListActivity extends GenericActivity implements ContextInteraction, MoviesListInteraction {

    @Inject
    MoviesListViewModel listViewModel;
    ActivityMoviesListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movies_list);
        injectDependencies();

        binding.setMoviesViewModel(listViewModel);
        listViewModel.setContextInteraction(this);
        listViewModel.setNotification(this);
        listViewModel.setStringInteraction(this);
        listViewModel.setInteraction(this);
        listViewModel.setupData();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        listViewModel.setupData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);

    }

    private void injectDependencies() {
        MainApplication.getAppComponent().inject(this);
    }

    @Override
    public Context getContext() {
        return getBaseContext();
    }

    @Override
    public ArrayList<MovieData> getMoviesList(Intent intent) {

        Bundle bundle = intent.getBundleExtra("bundle");
        ArrayList<MovieData> updated = new ArrayList<>();
        try {
            updated = (ArrayList<MovieData>) bundle.getSerializable("movies");
        }catch (Exception d){
            onBackPressed();
        }

        return updated;
    }

    @Override
    public void fetchList(List<MovieData> updated) {
        List<MovieData> empty = new ArrayList<>();
        MoviesListAdapter adapter = new MoviesListAdapter(getContext(), empty);
        binding.rvMovies.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        binding.rvMovies.setAdapter(adapter);
        binding.rvMovies.setHasFixedSize(true);
        adapter.updateList(updated);
    }

    @Override
    public Intent getExtras() {
        return getIntent();
    }
}
