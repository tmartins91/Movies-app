package com.tmartins.task.moviesapp.core.datasource;

import com.tmartins.task.moviesapp.core.api.MoviesResponse;
import com.tmartins.task.moviesapp.core.api.MoviesService;
import com.tmartins.task.moviesapp.core.model.Movie;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class MoviesRemoteDataSource implements MoviesDataSource {

    private MoviesService moviesService;

    @Inject
    public MoviesRemoteDataSource(MoviesService service) {
        moviesService = service;
    }

    @Override
    public Flowable<List<Movie>> loadMovies() {
        return moviesService.getNowPlaying().map(MoviesResponse::getMovieList);
    }

}
