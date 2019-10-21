package com.christine.dreamhouseapp.network;

import com.christine.dreamhouseapp.model.MovieTrailerResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetMovieTrailerService {

    @GET("movie/{id}/videos")
    Call<MovieTrailerResult> getTrailers(@Path("id") int movieId, @Query("api_key") String userkey);

}