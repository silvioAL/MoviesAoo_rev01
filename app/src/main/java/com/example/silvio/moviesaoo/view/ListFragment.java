package com.example.silvio.moviesaoo.view;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.silvio.moviesaoo.R;
import com.example.silvio.moviesaoo.data.entity.MovieData;
import com.example.silvio.moviesaoo.databinding.FragmentListBinding;
import com.example.silvio.moviesaoo.viewmodel.ListViewModel;

import java.util.List;

import javax.inject.Inject;


public class ListFragment extends Fragment {

    public static final String TAG = "ListFragment";
    private static final String MOVIES_LIST_KEY = "param1";
    List<MovieData> movies;
    @Inject
    ListViewModel listViewModel;
    FragmentListBinding binding;

    public ListFragment() {
        // Required empty public constructor
    }

    public static ListFragment newInstance(List<MovieData> param1) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putParcelable(MOVIES_LIST_KEY, (Parcelable) param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

        setRetainInstance(true);
    }

    public List<MovieData> getData() {
        return this.movies;
    }

    public void setData(List<MovieData> retained) {
        this.movies = retained;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false);

        return binding.getRoot();
    }

}
