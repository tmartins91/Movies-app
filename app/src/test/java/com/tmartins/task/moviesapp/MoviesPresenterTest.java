package com.tmartins.task.moviesapp;

import com.tmartins.task.moviesapp.core.datasource.MoviesRepository;
import com.tmartins.task.moviesapp.core.model.Movie;
import com.tmartins.task.moviesapp.ui.movies.MoviesContract;
import com.tmartins.task.moviesapp.ui.movies.MoviesPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;


@RunWith(Parameterized.class)
public class MoviesPresenterTest{

    private static final Movie MOVIE1 = new Movie();
    private static final Movie MOVIE2 = new Movie();
    private static final Movie MOVIE3 = new Movie();
    private static final Movie MOVIE4 = new Movie();
    private static final Movie MOVIE5 = new Movie();
    private static final List<Movie> EMPTY_MOVIES = Collections.emptyList();
    private static final List<Movie> FIVE_MOVIES = Arrays.asList(
            MOVIE1, MOVIE2, MOVIE3, MOVIE4, MOVIE5);

    @Parameterized.Parameters
    public static Object[] data() {
        return new Object[] { EMPTY_MOVIES, FIVE_MOVIES };
    }

    @Parameterized.Parameter
    public List<Movie> movies;
    @Mock
    private MoviesRepository repository;
    @Mock
    private MoviesContract.View view;

    private TestScheduler testScheduler;

    private MoviesPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        testScheduler = new TestScheduler();
        presenter = new MoviesPresenter(repository, view, testScheduler, testScheduler);
    }

    @Test
    public void loadMovies_ShouldAlwaysStopLoadingIndicatorOnView_WhenComplete() {
        given(repository.loadMovies()).willReturn(Flowable.just(movies));

        presenter.loadMovies();
        testScheduler.triggerActions();

        then(view).should(atLeastOnce()).stopLoadingIndicator();
    }

    @Test
    public void loadMovies_ShouldShowMovieOnView_WithDataReturned() {
        given(repository.loadMovies()).willReturn(Flowable.just(FIVE_MOVIES));

        presenter.loadMovies();
        testScheduler.triggerActions();

        then(view).should().showMovies(FIVE_MOVIES);
        then(view).should(atLeastOnce()).stopLoadingIndicator();
    }

    @Test
    public void loadMovies_ShouldShowMovie_WhenNoDataReturned() {
        given(repository.loadMovies()).willReturn(Flowable.just(EMPTY_MOVIES));

        presenter.loadMovies();
        testScheduler.triggerActions();

        then(view).should(never()).showMovies(any());
        then(view).should().showNoDataMessage();
        then(view).should(atLeastOnce()).stopLoadingIndicator();
    }

    @Test
    public void getMovies_ShouldShowDetailOnView() {
        given(repository.getMovie(1)).willReturn(Flowable.just(MOVIE1));

        presenter.getMovie(1);
        testScheduler.triggerActions();

        then(view).should().showMovieDetail(MOVIE1);
    }

}