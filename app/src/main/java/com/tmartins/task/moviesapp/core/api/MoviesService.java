package com.tmartins.task.moviesapp.core.api;

import com.tmartins.task.moviesapp.core.model.CollectionDetails;
import com.tmartins.task.moviesapp.core.model.MovieDetail;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MoviesService {

    @GET("movie/now_playing")
    Flowable<MoviesResponse> getNowPlaying();

    @GET("movie/{movie_id}")
    Flowable<MovieDetail> getMovieDetails(@Path("movie_id") long movieId);

    @GET("collection/{collection_id}")
    Flowable<CollectionDetails> getMovieCollections(@Path("collection_id") long collectionId);

}
