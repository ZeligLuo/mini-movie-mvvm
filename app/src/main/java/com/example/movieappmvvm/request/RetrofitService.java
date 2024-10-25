package com.example.movieappmvvm.request;

import com.example.movieappmvvm.Utils.Credentials;
import com.example.movieappmvvm.Utils.MovieApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private final static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(Credentials.API_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private final static Retrofit retrofit = retrofitBuilder.build();

    private final static MovieApi movieApi = retrofit.create(MovieApi.class);

    public static MovieApi getMovieApi() {
        return movieApi;
    }



}
