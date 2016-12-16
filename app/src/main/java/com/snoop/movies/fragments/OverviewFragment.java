package com.snoop.movies.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.snoop.movies.R;

import butterknife.ButterKnife;

import static com.snoop.movies.extras.Constants.EXTRA_MOVIE_OVERVIEW;
import static com.snoop.movies.extras.Constants.UNAVAILABLE_OVERVIEW;


/**
 * A simple {@link Fragment} subclass.
 */
public class OverviewFragment extends Fragment {

    public static OverviewFragment newInstance(String movieOverview){
        OverviewFragment overviewFragment = new OverviewFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_MOVIE_OVERVIEW, movieOverview);
        overviewFragment.setArguments(args);
        return overviewFragment;
    }

    public OverviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        String overview = "";

        // Bundle data from recycler view's click is recovered here to set the movie overview
        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.getString(EXTRA_MOVIE_OVERVIEW) != null) {
            overview = bundle.getString(EXTRA_MOVIE_OVERVIEW);
        }else {
            overview = UNAVAILABLE_OVERVIEW;
        }

        TextView tvOverview = ButterKnife.findById(view, R.id.tv_movie_overview);
        tvOverview.setText(overview);

        return view;
    }

}
