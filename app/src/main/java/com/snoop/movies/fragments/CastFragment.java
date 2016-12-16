package com.snoop.movies.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snoop.movies.adapters.CastRecAdapter;
import com.snoop.movies.R;
import com.snoop.movies.rest.MoviesAPI;
import com.snoop.movies.rest.RestClient;
import com.snoop.movies.rest.model.CastJsonModel;
import com.snoop.movies.rest.model.CastMember;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.snoop.movies.extras.Constants.EXTRA_MOVIE_ID;
import static com.snoop.movies.extras.MyApplication.API_KEY_THE_MOVIE_DATABASE;


/**
 * A simple {@link Fragment} subclass.
 */
public class CastFragment extends Fragment implements CastRecAdapter.RecyclerOnItemClickListener {

    public static final String LOG_TAG = CastFragment.class.getSimpleName();

    public static CastFragment newInstance(int movieId){
        CastFragment castFragment = new CastFragment();
        Bundle args = new Bundle();
        args.putInt(EXTRA_MOVIE_ID, movieId);
        castFragment.setArguments(args);
        return castFragment;
    }

    private RecyclerView recyclerView;
    private CastRecAdapter castRecAdapter;
    private int movieId;
    private List<CastMember> castMemberList;

    public CastFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rec_view, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            movieId = bundle.getInt(EXTRA_MOVIE_ID);
        }

        recyclerView = (RecyclerView) view.findViewById(R.id.rec_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        MoviesAPI service = RestClient.getInstance().getService();
        Call<CastJsonModel> call =  service.getCast(movieId, API_KEY_THE_MOVIE_DATABASE);
        call.enqueue(new Callback<CastJsonModel>() {
            @Override
            public void onResponse(Call<CastJsonModel> call, Response<CastJsonModel> response) {
                CastJsonModel castJsonModel = response.body();
                castMemberList = castJsonModel.getResults();

                castRecAdapter = new CastRecAdapter(getActivity(), castMemberList, CastFragment.this);
                recyclerView.setAdapter(castRecAdapter);
            }

            @Override
            public void onFailure(Call<CastJsonModel> call, Throwable t) {
                Log.d(LOG_TAG, "onFailure: " + t.toString());

            }
        });

        return view;
    }

    @Override
    public void onItemClick(int position) {
        // TODO Get Movies the actor has been in
    }
}
