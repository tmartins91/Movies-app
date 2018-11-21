package com.tmartins.task.moviesapp.repository;

import com.tmartins.task.moviesapp.core.datasource.MoviesDataSource;
import com.tmartins.task.moviesapp.core.datasource.MoviesRepository;
import com.tmartins.task.moviesapp.core.datasource.Remote;
import com.tmartins.task.moviesapp.core.model.Movie;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.subscribers.TestSubscriber;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

public class MoviesRepositoryTest{
    private static final Movie MOVIE1 = new Movie();
    private static final Movie MOVIE2 = new Movie();
    private static final Movie MOVIE3 = new Movie();
    private static final Movie MOVIE4 = new Movie();
    private static final Movie MOVIE5 = new Movie();
    private static final List<Movie> movies = Arrays.asList(
            MOVIE1, MOVIE2, MOVIE3, MOVIE4, MOVIE5);

    @Mock
    @Remote
    private MoviesDataSource remoteDataSource;

    private MoviesRepository repository;

    private TestSubscriber<List<Movie>> moviesTestSubscriber;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        repository = new MoviesRepository(remoteDataSource);

        moviesTestSubscriber = new TestSubscriber<>();
    }

    @Test
    public void loadMovies_ShouldReturnFromRemote() {
        doReturn(Flowable.just(movies)).when(remoteDataSource).loadMovies();

        repository.loadMovies().subscribe(moviesTestSubscriber);

        verify(remoteDataSource).loadMovies();

        assertEquals(repository.movieList, movies);

        moviesTestSubscriber.assertValue(movies);
    }

}