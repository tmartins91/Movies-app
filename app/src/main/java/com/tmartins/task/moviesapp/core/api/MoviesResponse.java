package com.tmartins.task.moviesapp.core.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tmartins.task.moviesapp.core.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MoviesResponse {

    @SerializedName("results")
    @Expose
    private List<Movie> movieList;

    public MoviesResponse() {
        this.movieList = new ArrayList<>();
    }

    public MoviesResponse(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

}
