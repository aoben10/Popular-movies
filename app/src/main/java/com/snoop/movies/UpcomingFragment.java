package com.snoop.movies;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.snoop.movies.adapters.RecAdapter;
import com.snoop.movies.extras.EndlessRecyclerViewScrollListener;
import com.snoop.movies.rest.MoviesAPI;
import com.snoop.movies.rest.RestClient;
import com.snoop.movies.rest.model.JsonResponseModel;
import com.snoop.movies.rest.model.MovieItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.snoop.movies.extras.Constants.EXTRA_MOVIE_ID;
import static com.snoop.movies.extras.Constants.EXTRA_MOVIE_OVERVIEW;
import static com.snoop.movies.extras.Constants.EXTRA_MOVIE_TITLE;
import static com.snoop.movies.extras.MyApplication.API_KEY_THE_MOVIE_DATABASE;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingFragment extends Fragment implements RecAdapter.RecyclerOnItemClickListener {

    public static final String LOG_TAG = UpcomingFragment.class.getSimpleName();

    public RecyclerView recyclerView;
    public RecAdapter recAdapter;
    private ProgressDialog progress;
    private List<MovieItem> movieItemList = new ArrayList<>();
    public List<MovieItem> totMovieList = new ArrayList<>();
    public int pageOne;

    public UpcomingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rec_view, container, false);

        loadProgressDialog(); // TODO: Fix progress dialog

        initializeScreen(view);
        httpRequestEnqueue(pageOne, getClass().toString());

        return view;
    }

    public void loadNextDataFromApi(int offset) {
        httpRequestEnqueue(pageOne + offset, getClass().toString());
        displayLoadingToast();
    }

    public void httpRequestEnqueue(final int pageNum, final String category) {
        MoviesAPI service = RestClient.getInstance().getService();
        Call<JsonResponseModel> call = service.getUpcomingMovies(API_KEY_THE_MOVIE_DATABASE, pageNum);

        // Create REST call based on specified fragment
        if(category.equals("class com.snoop.movies.UpcomingFragment")) {
            call = service.getUpcomingMovies(API_KEY_THE_MOVIE_DATABASE, pageNum);
        }else if(category.equals("class com.snoop.movies.fragments.NowPlayingFragment")){
            call = service.getNowPlayingMovies(API_KEY_THE_MOVIE_DATABASE, pageNum);
        }else if(category.equals("class com.snoop.movies.fragments.PopularFragment")){
            call = service.getPopularMovies(API_KEY_THE_MOVIE_DATABASE, pageNum);
        } else if(category.equals("class com.snoop.movies.fragments.TopRatedFragment")){
            call = service.getTopRatedMovies(API_KEY_THE_MOVIE_DATABASE, pageNum);
        }

        call.enqueue(new Callback<JsonResponseModel>() {
            @Override
            public void onResponse(Call<JsonResponseModel> call, Response<JsonResponseModel> response) {
                JsonResponseModel jsonResponseModel = response.body();
                movieItemList = jsonResponseModel.getResults();
                totMovieList.addAll(movieItemList);
                progress.dismiss();

                if (pageNum == pageOne) {
                    recAdapter.setMovieItemList(movieItemList);
                    recyclerView.setAdapter(recAdapter);

                } else {
                    recAdapter.addItems(movieItemList);
                }
            }

            @Override
            public void onFailure(Call<JsonResponseModel> call, Throwable t) {
                failedToGetDataToast(t);
                Log.d(LOG_TAG, "onFailure: " + t.toString());
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        MovieItem movieItem = totMovieList.get(position);
        Intent intent = new Intent(getActivity(), MovieDetails.class);
        intent.putExtra(EXTRA_MOVIE_ID, movieItem.getId());
        intent.putExtra(EXTRA_MOVIE_OVERVIEW, movieItem.getOverview());
        intent.putExtra(EXTRA_MOVIE_TITLE, movieItem.getTitle());
        startActivity(intent);
    }

    public void failedToGetDataToast(Throwable t) {
        // Display a toast message to the user with appropriate message

        /*if (t instanceof TimeoutError || t instanceof NoConnectionError) {
            Toast.makeText(getActivity(), "Timeout error", Toast.LENGTH_LONG).show();
        } else if (t instanceof AuthFailureError) {
            Toast.makeText(getActivity(), "Error: Authentication failed", Toast.LENGTH_LONG).show();
        } else if (t instanceof ServerError) {
            Toast.makeText(getActivity(), "Server error", Toast.LENGTH_LONG).show();
        } else if (t instanceof NetworkError) {
            Toast.makeText(getActivity(), "Network error", Toast.LENGTH_LONG).show();
        } else if (t instanceof ParseError) {
            Toast.makeText(getActivity(), "Server parse error", Toast.LENGTH_LONG).show();
        }*/
    }

    public void loadProgressDialog() {
        progress = new ProgressDialog(getContext());
        progress.setTitle(getString(R.string.loading));
        progress.setCancelable(true);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
    }

    public void initializeScreen(View view) {

        pageOne = 1;
        recyclerView = (RecyclerView) view.findViewById(R.id.rec_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(linearLayoutManager);
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadNextDataFromApi(page);
            }
        };

        recyclerView.addOnScrollListener(scrollListener);

        recAdapter = new RecAdapter(getActivity());
        recAdapter.setCommunicator(this);
    }

    public void displayLoadingToast() {
        final Toast toast = Toast.makeText(getActivity(), R.string.Loading3dots, Toast.LENGTH_SHORT);
        toast.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 400);
    }
}
