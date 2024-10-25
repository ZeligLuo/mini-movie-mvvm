package com.example.movieappmvvm.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieappmvvm.AppExcecuter;
import com.example.movieappmvvm.Utils.Credentials;
import com.example.movieappmvvm.models.MovieModel;
import com.example.movieappmvvm.response.SearchMovieResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {

    // live data
    private final MutableLiveData<List<MovieModel>> mMovies;

    private static MovieApiClient instance;

    // global runnable
    private RetrieveMoviesRunnable retrieveMoviesRunnable;

    public static MovieApiClient getInstance() {
        if(instance == null) {
            instance = new MovieApiClient();
        }
        return instance;
    }

    private MovieApiClient() {
        mMovies = new MutableLiveData<>();
    }

    public LiveData<List<MovieModel>> getMovies() {
        return mMovies;
    }

    public void searchMoviesApi(String query, int pageNumber) {
        if(retrieveMoviesRunnable != null) {
            retrieveMoviesRunnable = null;
        }

        retrieveMoviesRunnable = new RetrieveMoviesRunnable(query, pageNumber);

        final Future<?> handler = AppExcecuter.getInstance().networkIO().submit(retrieveMoviesRunnable);

        AppExcecuter.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // cancel retrofit call
                handler.cancel(true);
            }
        }, 4000, TimeUnit.MILLISECONDS);
    }

    // retrieve data REST Api
    private class RetrieveMoviesRunnable implements Runnable {

        private final String query;
        private final int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            // get response object
            try {
                Response response = getMovies(query, pageNumber).execute();
                if(cancelRequest) {
                    return;
                }
                if(response.code() == 200) {
                    List<MovieModel> list = new ArrayList<>(((SearchMovieResponse)response.body()).getMovies());
                    if(pageNumber == 1) {
                        mMovies.postValue(list);
                    } else {
                        List<MovieModel> currentMovies = mMovies.getValue();
                        currentMovies.addAll(list);
                        mMovies.postValue(currentMovies);
                    }
                } else {
                    String error = response.errorBody().string();
                    Log.v("MSG", "Error" + error);
                    mMovies.postValue(null);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if(cancelRequest) {
                return;
            }
        }

        // search method
        private Call<SearchMovieResponse> getMovies(String query, int pageNumber) {
            return RetrofitService.getMovieApi().searchMovie(
                    Credentials.API_KEY,
                    query,
                    pageNumber
            );
        }

        private void CancelRequest() {
            Log.v("MSG", "Cancelling search request");
            cancelRequest = true;
        }

    }

}
