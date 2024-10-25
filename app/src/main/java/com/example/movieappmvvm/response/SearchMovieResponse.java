package com.example.movieappmvvm.response;

import androidx.annotation.NonNull;

import com.example.movieappmvvm.models.MovieModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchMovieResponse {

    @SerializedName("total_results")
    @Expose()
    private final int total_count;

    @SerializedName("results")
    @Expose()
    private final List<MovieModel> movies;

    public SearchMovieResponse(int total_count, List<MovieModel> movies) {
        this.total_count = total_count;
        this.movies = movies;
    }

    public int getTotal_count() {
        return total_count;
    }

    public List<MovieModel> getMovies() {
        return movies;
    }

    @NonNull
    @Override
    public String toString() {
        return "SearchMovieResponse{" +
                "total_count=" + total_count +
                ", movies=" + movies +
                '}';
    }
}
