package com.snoop.movies.rest;

import com.snoop.movies.rest.model.CastJsonModel;
import com.snoop.movies.rest.model.InfoModelObj;
import com.snoop.movies.rest.model.JsonResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.snoop.movies.extras.UrlEndpoints.ENDPOINT_NOWPLAYING;
import static com.snoop.movies.extras.UrlEndpoints.ENDPOINT_POPULAR;
import static com.snoop.movies.extras.UrlEndpoints.ENDPOINT_TOP_RATED;
import static com.snoop.movies.extras.UrlEndpoints.ENDPOINT_UPCOMING;
import static com.snoop.movies.extras.UrlEndpoints.URL_PARAM_API_KEY;
import static com.snoop.movies.extras.UrlEndpoints.URL_PARAM_PAGE;

/**
 * Created by galaxywizkid on 12/8/16.
 */

public interface MoviesAPI {

    @GET(ENDPOINT_UPCOMING)
    Call<JsonResponseModel> getUpcomingMovies(@Query(URL_PARAM_API_KEY) String apiKey, @Query(URL_PARAM_PAGE) int pageNum);

    @GET(ENDPOINT_NOWPLAYING)
    Call<JsonResponseModel> getNowPlayingMovies(@Query(URL_PARAM_API_KEY) String apiKey, @Query(URL_PARAM_PAGE) int pageNum);

    @GET(ENDPOINT_POPULAR)
    Call<JsonResponseModel> getPopularMovies(@Query(URL_PARAM_API_KEY) String apiKey, @Query(URL_PARAM_PAGE) int pageNum);

    @GET(ENDPOINT_TOP_RATED)
    Call<JsonResponseModel> getTopRatedMovies(@Query(URL_PARAM_API_KEY) String apiKey,@Query(URL_PARAM_PAGE) int pageNum);

    @GET("{movieId}/credits")
    Call<CastJsonModel> getCast(@Path("movieId") int movieId, @Query(URL_PARAM_API_KEY) String apiKey);

    @GET("{movieId}")
    Call<InfoModelObj> getInfo(@Path("movieId") int movieId, @Query(URL_PARAM_API_KEY) String apiKey, @Query("append_to_response") String videos);

    @GET("{movieId}/similar")
    Call<JsonResponseModel> getSimilarMovies(@Path("movieId") int movieId, @Query(URL_PARAM_API_KEY) String apiKey);



}
