package com.example.silvio.moviesaoo.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.silvio.moviesaoo.R;
import com.example.silvio.moviesaoo.data.entity.MovieData;
import com.example.silvio.moviesaoo.databinding.ItemCardMovieBinding;
import com.example.silvio.moviesaoo.viewholder.MovieCardViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by silvio on 25/12/2017.
 */

public class MoviesListAdapter extends RecyclerView.Adapter<MovieCardViewHolder> {

    List<MovieData> movies = new ArrayList<>();

    @Override
    public MovieCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemCardMovieBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.item_card_movie
                , parent
                , false);

        return new MovieCardViewHolder(binding);
    }

    public void updateList(List<MovieData> movieData) {
        if (movieData != null) {
            movies.clear();
            movies.addAll(movieData);
            notifyDataSetChanged();
        }
    }

    @Override
    public void onBindViewHolder(MovieCardViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
