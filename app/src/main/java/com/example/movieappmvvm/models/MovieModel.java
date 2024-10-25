package com.example.movieappmvvm.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class MovieModel implements Parcelable {

    private final int movieID;
    private final String title;
    private final String posterPath;
    private final String releaseDate;
    private final String movieOverview;
    private final float voteAverage;

    public MovieModel(int movieID, String title, String posterPath, String releaseDate, String movieOverview, float voteAverage) {
        this.movieID = movieID;
        this.title = title;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.movieOverview = movieOverview;
        this.voteAverage = voteAverage;
    }

    protected MovieModel(Parcel in) {
        movieID = in.readInt();
        title = in.readString();
        posterPath = in.readString();
        releaseDate = in.readString();
        movieOverview = in.readString();
        voteAverage = in.readFloat();
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    public int getMovieId() {
        return movieID;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public float getVoteAverage() {
        return voteAverage;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(movieID);
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeString(releaseDate);
        dest.writeString(movieOverview);
        dest.writeFloat(voteAverage);
    }
}
