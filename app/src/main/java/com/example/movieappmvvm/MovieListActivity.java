package com.example.movieappmvvm;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.movieappmvvm.Utils.Credentials;
import com.example.movieappmvvm.Utils.MovieApi;
import com.example.movieappmvvm.models.MovieModel;
import com.example.movieappmvvm.request.RetrofitService;
import com.example.movieappmvvm.response.SearchMovieResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends AppCompatActivity {

    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetRetrofitResponse();
            }
        });

    }

    private void GetRetrofitResponse() {
        MovieApi movieApi = RetrofitService.getMovieApi();

        Call<SearchMovieResponse> responseCall = movieApi
                .searchMovie(
                        Credentials.API_KEY,
                        "Jack Reacher",
                        "1"
                );

        responseCall.enqueue(new Callback<SearchMovieResponse>() {
            @Override
            public void onResponse(Call<SearchMovieResponse> call, Response<SearchMovieResponse> response) {
                if(response.code() == 200) {
                    Log.v("Tag", "The response" + response.body().toString());

                    List<MovieModel> movies = new ArrayList<>(response.body().getMovies());

                    for(MovieModel movie: movies) {
                        Log.v("Tag", "The list" + movie.getTitle());
                    }
                }
                else {
                    Log.v("Tag", "Error" + response.errorBody().toString());
                }

            }

            @Override
            public void onFailure(Call<SearchMovieResponse> call, Throwable throwable) {

            }
        });
    }
}