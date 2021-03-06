package com.example.silvio.moviesaoo.viewholder;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

import com.example.silvio.moviesaoo.BR;
import com.example.silvio.moviesaoo.data.entity.MovieData;
import com.example.silvio.moviesaoo.databinding.ItemCardMovieBinding;
import com.example.silvio.moviesaoo.view.MovieDetailsActivity;

/**
 * Created by silvio on 25/12/2017.
 */

public class MovieCardViewHolder extends RecyclerView.ViewHolder {

    public String movieImgUrl;
    public String title;
    private ViewDataBinding binding;
    private MovieData dataMovie;


    public MovieCardViewHolder(ItemCardMovieBinding itemView) {
        super(itemView.getRoot());
        binding = itemView;
    }

    public void bind(MovieData movie){
        dataMovie = movie;
        binding.setVariable(BR.holder, this);
        title = movie.getTitle();
        movieImgUrl = "https://image.tmdb.org/t/p/w500" + movie.getPoster_path();
        binding.executePendingBindings();
    }

    public void moveToDetails(){
        Intent intent = new Intent(itemView.getContext(), MovieDetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("movie", dataMovie);
        itemView.getContext().startActivity(intent);
    }
}
