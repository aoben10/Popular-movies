package com.snoop.movies;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.snoop.movies.adapters.RelatedMovsRecAdapter;
import com.snoop.movies.rest.MoviesAPI;
import com.snoop.movies.rest.RestClient;
import com.snoop.movies.rest.model.GenreObj;
import com.snoop.movies.rest.model.InfoModelObj;
import com.snoop.movies.rest.model.JsonResponseModel;
import com.snoop.movies.rest.model.MovieItem;
import com.snoop.movies.rest.model.ProdCompObj;
import com.snoop.movies.rest.model.ProdCountryObj;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.snoop.movies.extras.Constants.EXTRA_MOVIE_ID;
import static com.snoop.movies.extras.Constants.EXTRA_MOVIE_OVERVIEW;
import static com.snoop.movies.extras.Constants.EXTRA_MOVIE_TITLE;
import static com.snoop.movies.extras.MyApplication.API_KEY_THE_MOVIE_DATABASE;
import static com.snoop.movies.extras.UrlEndpoints.URL_BACKDROP_PREFIX;
import static com.snoop.movies.extras.UrlEndpoints.URL_POSTER_PREFIX;


/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment implements RelatedMovsRecAdapter.RecyclerOnItemClickListener {

    public static InfoFragment newInstance(int movieId){
        InfoFragment infoFragment = new InfoFragment();
        Bundle args = new Bundle();
        args.putInt(EXTRA_MOVIE_ID, movieId);
        infoFragment.setArguments(args);
        return infoFragment;
    }

    private RecyclerView recyclerView;
    private RelatedMovsRecAdapter relatedMovsRecAdapter;
    private List<MovieItem> relatedMoviesList = new ArrayList<>();
    private PassDataToFAB mDataPasser;

    private int movieId;
    private ProgressDialog progress;
    private DateFormat outFormat = new SimpleDateFormat("MM-dd-yy", Locale.US);
    @BindView(R.id.im_movie_backdrop)ImageView backdrop;
    @BindView(R.id.im_movie_poster_icon)ImageView poster;
    @BindView(R.id.tv_movie_title2)TextView title;
    @BindView(R.id.tv_tagline)TextView tagLine;
    @BindView(R.id.tv_runtime)TextView runTime;
    @BindView(R.id.tv_status)TextView status;
    @BindView(R.id.tv_release_date)TextView releaseDate;
    @BindView(R.id.tv_num_votes)TextView numVotes;
    @BindView(R.id.ic_movie_ratings)RatingBar ratingsBar;
    @BindView(R.id.tv_prod_country)TextView prodCountry;
    @BindView(R.id.tv_prod_companies)TextView prodCompanies;
    @BindView(R.id.tv_genres)TextView tvGenres;
    @BindView(R.id.layout_below_details_display)View layoutBelowDetDisp;


    public InfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        loadProgressDialog();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            movieId = bundle.getInt(EXTRA_MOVIE_ID);
        }
        ButterKnife.bind(this, view);

        recyclerView = (RecyclerView) view.findViewById(R.id.rec_view_grid);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        MoviesAPI service = RestClient.getInstance().getService();
        httpMovieInfoRequestEnqueue(service);
        httpSimilarMoviesRequestEnqueue(service);

        Call<JsonResponseModel> callSimilarMovs = service.getSimilarMovies(movieId, API_KEY_THE_MOVIE_DATABASE);
        callSimilarMovs.enqueue(new Callback<JsonResponseModel>() {
            @Override
            public void onResponse(Call<JsonResponseModel> call, Response<JsonResponseModel> response) {
                JsonResponseModel jsonResponseModel = response.body();
                relatedMoviesList = jsonResponseModel.getResults();

                if(relatedMoviesList.size() != 0) {
                    relatedMovsRecAdapter = new RelatedMovsRecAdapter(getActivity(), relatedMoviesList, InfoFragment.this);
                    recyclerView.setAdapter(relatedMovsRecAdapter);
                }else{
                    layoutBelowDetDisp.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<JsonResponseModel> call, Throwable t) {

            }
        });

        return view;
    }

    public void httpMovieInfoRequestEnqueue(MoviesAPI service){
        Call<InfoModelObj> call = service.getInfo(movieId, API_KEY_THE_MOVIE_DATABASE, "videos");
        call.enqueue(new Callback<InfoModelObj>() {
            @Override
            public void onResponse(Call<InfoModelObj> call, Response<InfoModelObj> response) {
                InfoModelObj infoModelObj = response.body();
                infoModelObj.getStatus();

                setViews(infoModelObj);
                setImages(infoModelObj);
            }

            @Override
            public void onFailure(Call<InfoModelObj> call, Throwable t) {

            }
        });
    }

    public void httpSimilarMoviesRequestEnqueue(MoviesAPI service){

        Call<JsonResponseModel> callSimilarMovs = service.getSimilarMovies(movieId, API_KEY_THE_MOVIE_DATABASE);
        callSimilarMovs.enqueue(new Callback<JsonResponseModel>() {
            @Override
            public void onResponse(Call<JsonResponseModel> call, Response<JsonResponseModel> response) {
                JsonResponseModel jsonResponseModel = response.body();
                relatedMoviesList = jsonResponseModel.getResults();

                if(relatedMoviesList.size() != 0) {
                    relatedMovsRecAdapter = new RelatedMovsRecAdapter(getActivity(), relatedMoviesList, InfoFragment.this);
                    recyclerView.setAdapter(relatedMovsRecAdapter);
                }else{
                    layoutBelowDetDisp.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<JsonResponseModel> call, Throwable t) {

            }
        });

    }

    public void setViews(InfoModelObj infoModelObj){

        title.setText(infoModelObj.getTitle());
        status.setText(infoModelObj.getStatus());
        if(infoModelObj.getTagLine() != null && !infoModelObj.getTagLine().isEmpty()){
            String tagText = "\"" + infoModelObj.getTagLine() + "\"";
            tagLine.setText(tagText);
        }

        String FABUrl = "";
        String youtubeKey = "";
        if(infoModelObj.getHomePageURL() != null && !infoModelObj.getHomePageURL().isEmpty()){
            FABUrl = infoModelObj.getHomePageURL();
        }
        if(infoModelObj.getVideos() != null){
            if(infoModelObj.getVideos().getVideoResults() != null && !infoModelObj.getVideos().getVideoResults().isEmpty()){
                youtubeKey = infoModelObj.getVideos().getVideoResults().get(0).getKey();
            }
        }

        passFABData(FABUrl, youtubeKey);

        if(infoModelObj.getRunTime() != -1){
            String runtimeStr = infoModelObj.getRunTime() + " min.";
            runTime.setText(runtimeStr);
        }

        if (infoModelObj.getVoteAvg() != 0) {
            float rating = ((infoModelObj.getVoteAvg() / 10.0F) * 5.0F);
            ratingsBar.setRating(rating);
        }

        if (infoModelObj.getVoteCount() != -1) {
            String votes = infoModelObj.getVoteCount() + " votes";
            numVotes.setText(votes);
        }

        if (infoModelObj.getReleaseDate() != null) {
            String formattedDate = "(" + outFormat.format(infoModelObj.getReleaseDate()) + ")";
            releaseDate.setText(formattedDate);
        }

        // Set production companies
        if (infoModelObj.getProductionCompanies() != null && !infoModelObj.getProductionCompanies().isEmpty() ) {
            StringBuilder builder = new StringBuilder();

            for (ProdCompObj prodComp : infoModelObj.getProductionCompanies()) {
                builder.append(prodComp.getCompanyName());
                builder.append("\n");
            }
            prodCompanies.setText(builder.toString().trim());
        }

        // Set movie genres
        List<GenreObj> genresList = infoModelObj.getGenres();
        if(genresList != null && !genresList.isEmpty()){
            StringBuilder builder = new StringBuilder();

            for (int i = 0; i < genresList.size(); i++) {
                builder.append(genresList.get(i).getGenreName());
                if (i != genresList.size() - 1) builder.append(", ");
                else builder.append("\n");
            }
            tvGenres.setText(builder.toString());
        }

        // Set production countries
        if (infoModelObj.getProductionCountries().size() != 0) {
            StringBuilder builder = new StringBuilder();

            for (ProdCountryObj country : infoModelObj.getProductionCountries()) {
                builder.append(country.getCountryName());
                builder.append("\n");
            }
            prodCountry.setText(builder.toString().trim());
        }
    }

    public void setImages(InfoModelObj infoModelObj){

        // Set the backdrop
        String backdropSuffix = infoModelObj.getBackdropSuffix();
        if (backdropSuffix != null && !backdropSuffix.isEmpty()) {
            String urlThumbnail = URL_BACKDROP_PREFIX + backdropSuffix;
            backdrop.setScaleType(ImageView.ScaleType.FIT_XY);
            // Fetch images using Picasso library and set them to corresponding ImageView
            Picasso.with(getActivity())
                    .load(urlThumbnail)
                    .into(backdrop, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            progress.dismiss();
                        }

                        @Override
                        public void onError() {

                        }
                    });
        }else{
            Drawable profileDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.im_image_shutter);
            backdrop.setImageDrawable(profileDrawable);
        }

        // Set the poster
        String posterSuffix = infoModelObj.getPosterSuffix();
        if (posterSuffix != null && !posterSuffix.isEmpty()) {
            String urlThumbnail = URL_POSTER_PREFIX + posterSuffix;
            poster.setScaleType(ImageView.ScaleType.FIT_XY);

            // Fetch images using Picasso library and set them to corresponding ImageView
            Picasso.with(getActivity())
                    .load(urlThumbnail)
                    .into(poster);
        }else{
            Drawable profileDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.im_image_shutter);
            poster.setImageDrawable(profileDrawable);
        }
    }

    public void loadProgressDialog(){
        progress = new ProgressDialog(getContext());
        progress.setTitle(getString(R.string.loading));
        progress.setCancelable(true);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
    }

    @Override
    public void onItemClick(int position) {
        MovieItem movieItem = relatedMoviesList.get(position);
        Intent intent = new Intent(getActivity(), MovieDetails.class);
        intent.putExtra(EXTRA_MOVIE_ID, movieItem.getId());
        intent.putExtra(EXTRA_MOVIE_OVERVIEW, movieItem.getOverview());
        intent.putExtra(EXTRA_MOVIE_TITLE, movieItem.getTitle());
        startActivity(intent);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mDataPasser = (PassDataToFAB) context;
    }

    public void passFABData(String homepageURL, String youtubeKey){
        mDataPasser.onDataPass(homepageURL, youtubeKey);
    }

    public interface PassDataToFAB{
        void onDataPass(String homepageURL, String youtubeKey);
    }
}
