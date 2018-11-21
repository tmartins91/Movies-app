package com.tmartins.task.moviesapp.repository;

import com.tmartins.task.moviesapp.core.api.MoviesResponse;
import com.tmartins.task.moviesapp.core.api.MoviesService;
import com.tmartins.task.moviesapp.core.datasource.MoviesRemoteDataSource;
import com.tmartins.task.moviesapp.core.model.Movie;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.subscribers.TestSubscriber;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

public class MoviesRemoteDataSourceTest {

    @Mock
    MoviesService moviesService;

    private MoviesRemoteDataSource remoteDataSource;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        remoteDataSource = new MoviesRemoteDataSource(moviesService);
    }

    @Test
    public void loadMovies_ShouldReturnFromRemoteService() {
        MoviesResponse movieResponse = new MoviesResponse();
        TestSubscriber<List<Movie>> subscriber = new TestSubscriber<>();
        given(moviesService.getNowPlaying()).willReturn(Flowable.just(movieResponse));

        remoteDataSource.loadMovies().subscribe(subscriber);

        then(moviesService).should().getNowPlaying();
    }

}
