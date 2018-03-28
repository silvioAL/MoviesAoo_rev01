package com.example.silvio.moviesaoo.viewholder;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.silvio.moviesaoo.BR;
import com.example.silvio.moviesaoo.data.model.ResultsResponseModel;

/**
 * Created by silvioallgayertrindade on 27/03/2018.
 */

public class ReviewViewHolder extends RecyclerView.ViewHolder {

    ViewDataBinding binding;

    public ReviewViewHolder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }

    public void bind(ResultsResponseModel resultsResponseModel) {

        binding.setVariable(BR.reviewHolder, this);
        binding.setVariable(BR.review, resultsResponseModel);
        binding.executePendingBindings();
    }
}
