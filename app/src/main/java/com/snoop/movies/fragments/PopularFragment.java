package com.snoop.movies.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snoop.movies.UpcomingFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class PopularFragment extends UpcomingFragment {

    public PopularFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.pageOne = 1;
        //httpRequestEnqueue(pageOne, getClass().toString());
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
