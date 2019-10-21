package com.christine.dreamhouseapp.network;

import com.christine.dreamhouseapp.model.MoviePageResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetMovieService {
    @GET("movie/popular")
    Call<MoviePageResult>getPopularMovies(@Query("page") int page, @Query("api_key")String userkey);

    @GET("movie/top_rated")
    Call<MoviePageResult> getTopRatedMovies(@Query("page") int page, @Query("api_key") String userkey);

}