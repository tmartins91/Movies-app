package com.tmartins.task.moviesapp.core.datasource;

import android.support.annotation.VisibleForTesting;

import com.tmartins.task.moviesapp.core.model.Movie;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class MoviesRepository implements MoviesDataSource {

    private MoviesDataSource remoteDataSource;

    @VisibleForTesting
    public List<Movie> movieList;

    @Inject
    public MoviesRepository(@Remote MoviesDataSource dataSource) {
        remoteDataSource = dataSource;
        movieList = new ArrayList<>();
    }

    @Override
    public Flowable<List<Movie>> loadMovies(){
        return remoteDataSource.loadMovies().doOnNext(list -> {
            movieList.clear();
        }).flatMap(Flowable::fromIterable).doOnNext(movie -> {
            movieList.add(movie);
        }).toList().toFlowable();
    }

    public Flowable<Movie> getMovie(long id) {
        return Flowable.fromIterable(movieList).filter(movie -> movie.getId() == id);
    }

}
