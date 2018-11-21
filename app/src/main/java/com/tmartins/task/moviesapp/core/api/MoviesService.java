package com.tmartins.task.moviesapp.core.api;

import io.reactivex.Flowable;
import retrofit2.http.GET;

public interface MoviesService {

    @GET("movie/now_playing")
    Flowable<MoviesResponse> getNowPlaying();

}
