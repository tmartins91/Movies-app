package com.tmartins.task.moviesapp.core.datasource;

import com.tmartins.task.moviesapp.core.model.Movie;

import java.util.List;

import io.reactivex.Flowable;

public interface MoviesDataSource {

    Flowable<List<Movie>> loadMovies();

}
