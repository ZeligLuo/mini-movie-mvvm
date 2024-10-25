package com.example.movieappmvvm.Utils;

import com.example.movieappmvvm.models.MovieModel;
import com.example.movieappmvvm.response.SearchMovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

    @GET(".")
    Call<SearchMovieResponse> searchMovie (
            @Query("api_key") String api_key,
            @Query("query") String query,
            @Query("page") int page
    );

    @GET("3/movie/{movie_id}?")
    Call<MovieModel> getMovieByID(
            @Path("movie_id") int movieID,
            @Query("api_key") String api_key
    );

}
