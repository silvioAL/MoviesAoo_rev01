package com.example.silvio.moviesaoo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.silvio.moviesaoo.R;
import com.example.silvio.moviesaoo.viewholder.TrailerCardViewHolder;

import java.util.List;

/**
 * Created by silvioallgayertrindade on 19/03/2018.
 */

public class TrailersAdapter extends RecyclerView.Adapter<TrailerCardViewHolder> {

    LayoutInflater mInflater;
    List<String> urls;

    public TrailersAdapter(Context context, List<String> urls) {
        this.mInflater = LayoutInflater.from(context);
        this.urls = urls;
    }

    @Override
    public TrailerCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_movie_trailer, parent, false);
        return new TrailerCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerCardViewHolder holder, int position) {
        holder.bind(urls.get(position));
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }
}
