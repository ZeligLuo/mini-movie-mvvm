package com.example.movieappmvvm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.movieappmvvm.Utils.Credentials;
import com.example.movieappmvvm.Utils.MovieApi;
import com.example.movieappmvvm.models.MovieModel;
import com.example.movieappmvvm.request.RetrofitService;
import com.example.movieappmvvm.response.SearchMovieResponse;
import com.example.movieappmvvm.viewmodels.MovieListViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends AppCompatActivity {

    Button btn;

    // View model
    private MovieListViewModel movieListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.button);

        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);



    }

    // Observe data change
    private void ObserveChange() {
        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {

            }
        });
    }

    private void GetRetrofitResponse() {
        MovieApi movieApi = RetrofitService.getMovieApi();

        Call<SearchMovieResponse> responseCall = movieApi
                .searchMovie(
                        Credentials.API_KEY,
                        "Action",
                        1
                );

        responseCall.enqueue(new Callback<SearchMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<SearchMovieResponse> call, @NonNull Response<SearchMovieResponse> response) {
                if(response.code() == 200) {
                    assert response.body() != null;
                    Log.v("Tag", "The response" + response.body());

                    List<MovieModel> movies = new ArrayList<>(response.body().getMovies());

                    for(MovieModel movie: movies) {
                        Log.v("MSG", "The list" + movie.getReleaseDate());
                    }
                }
                else {
                    try {
                        assert response.errorBody() != null;
                        Log.v("MSG", "Error" + response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<SearchMovieResponse> call, @NonNull Throwable throwable) {
                throwable.fillInStackTrace();
            }
        });

    }

    private void GetRetrofitResponseByMovieID() {
        MovieApi movieApi = RetrofitService.getMovieApi();
        Call<MovieModel> responseCall = movieApi
                .getMovieByID(
                        550,
                        Credentials.API_KEY
                );

        responseCall.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(@NonNull Call<MovieModel> call, @NonNull Response<MovieModel> response) {
                if(response.code() == 200) {
                    MovieModel movie = response.body();
                    assert movie != null;
                    Log.v("MSG", "The response" + movie.getTitle());
                }
                else {
                    try {
                        assert response.errorBody() != null;
                        Log.v("MSG", "Error" + response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<MovieModel> call, @NonNull Throwable throwable) {
                throwable.fillInStackTrace();
            }
        });
    }

}