package com.example.silvio.moviesaoo.viewholder;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.silvio.moviesaoo.BR;
import com.example.silvio.moviesaoo.model.MovieData;
import com.example.silvio.moviesaoo.view.MovieDetailsActivity;

/**
 * Created by silvio on 25/12/2017.
 */

public class MovieCardViewHolder extends RecyclerView.ViewHolder {

    public String movieImgUrl;
    public String title;
    private ViewDataBinding binding;
    private MovieData dataMovie;


    public MovieCardViewHolder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
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
