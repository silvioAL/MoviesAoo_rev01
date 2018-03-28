package com.example.silvio.moviesaoo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.silvio.moviesaoo.R;
import com.example.silvio.moviesaoo.data.model.ResultsResponseModel;
import com.example.silvio.moviesaoo.viewholder.ReviewViewHolder;

import java.util.List;

/**
 * Created by silvioallgayertrindade on 27/03/2018.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewViewHolder> {

    Context context;
    List<ResultsResponseModel> responseModelList;
    LayoutInflater mInflater;


    public ReviewsAdapter(Context context, List<ResultsResponseModel> responseModelList) {
        this.context = context;
        this.responseModelList = responseModelList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReviewViewHolder(mInflater.inflate(R.layout.item_movie_review_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        holder.bind(responseModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return responseModelList.size();
    }
}
