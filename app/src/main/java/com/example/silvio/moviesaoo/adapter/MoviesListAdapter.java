package com.example.silvio.moviesaoo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.silvio.moviesaoo.R;
import com.example.silvio.moviesaoo.data.entity.MovieData;
import com.example.silvio.moviesaoo.viewholder.MovieCardViewHolder;

import java.util.List;

/**
 * Created by silvio on 25/12/2017.
 */

public class MoviesListAdapter extends RecyclerView.Adapter<MovieCardViewHolder> {

    Context mContext;
    LayoutInflater mInflater;
    List<MovieData> movies;

    public MoviesListAdapter(Context mContext, List<MovieData> movies) {
        this.mContext = mContext;
        this.movies = movies;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MovieCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       final View view = mInflater.inflate(R.layout.item_card_movie, parent, false);
        return new MovieCardViewHolder(view);
    }

    public void updateList(List<MovieData> movieData) {
        movies.clear();
        movies.addAll(movieData);
        notifyDataSetChanged();
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
