package com.snoop.movies.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snoop.movies.adapters.RecAdapter;
import com.snoop.movies.UpcomingFragment;
import com.snoop.movies.rest.model.MovieItem;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopRatedFragment extends UpcomingFragment {

    public static final String LOG_TAG = TopRatedFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private RecAdapter recAdapter;
    private List<MovieItem> movieItemList;

    public TopRatedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.pageOne = 1;
        //httpRequestEnqueue(pageOne, getClass().toString());
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}