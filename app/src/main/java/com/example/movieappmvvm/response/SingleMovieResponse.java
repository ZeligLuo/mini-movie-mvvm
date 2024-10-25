package com.example.movieappmvvm.response;

import androidx.annotation.NonNull;

import com.example.movieappmvvm.models.MovieModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingleMovieResponse {

    @SerializedName("results")
    @Expose()
    private final MovieModel movie;

    public SingleMovieResponse(MovieModel movie) {
        this.movie = movie;
    }

    public MovieModel getMovie() {
        return movie;
    }

    @NonNull
    @Override
    public String toString() {
        return "SingleMovieResponse{" +
                "movie=" + movie +
                '}';
    }


}
