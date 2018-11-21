package com.tmartins.task.moviesapp.ui.movies;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;

import com.tmartins.task.moviesapp.core.datasource.MoviesRepository;
import com.tmartins.task.moviesapp.core.model.Movie;
import com.tmartins.task.moviesapp.helpers.schedulers.RunOn;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.tmartins.task.moviesapp.helpers.schedulers.SchedulerType.IO;
import static com.tmartins.task.moviesapp.helpers.schedulers.SchedulerType.UI;

public class MoviesPresenter implements MoviesContract.Presenter, LifecycleObserver {

    private MoviesRepository repository;

    private MoviesContract.View view;

    private Scheduler ioScheduler;
    private Scheduler uiScheduler;

    private CompositeDisposable disposeBag;

    @Inject
    public MoviesPresenter(MoviesRepository repository, MoviesContract.View view,
                           @RunOn(IO) Scheduler ioScheduler,
                           @RunOn(UI) Scheduler uiScheduler) {
        this.repository = repository;
        this.view = view;
        this.ioScheduler = ioScheduler;
        this.uiScheduler = uiScheduler;

        if (view instanceof LifecycleOwner) {
            ((LifecycleOwner) view).getLifecycle().addObserver(this);
        }

        disposeBag = new CompositeDisposable();
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onAttach() {
        loadMovies();
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onDetach() {
        disposeBag.clear();
    }

    @Override
    public void loadMovies() {
        Disposable disposable = repository.loadMovies()
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe(
                        this::handleReturnedData,
                        this::handleError,
                        () -> view.stopLoadingIndicator());

        disposeBag.add(disposable);
    }

    @Override
    public void getMovie(long movieId) {
        Disposable disposable = repository.getMovie(movieId)
                .filter(movie -> movie != null)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe(movie -> view.showMovieDetail(movie));
        disposeBag.add(disposable);
    }

    private void handleReturnedData(List<Movie> list) {
        view.stopLoadingIndicator();
        if (list != null && !list.isEmpty()) {
            view.showMovies(list);
        } else {
            view.showNoDataMessage();
        }
    }

    private void handleError(Throwable error) {
        view.stopLoadingIndicator();
        view.showErrorMessage(error.getLocalizedMessage());
    }

}
