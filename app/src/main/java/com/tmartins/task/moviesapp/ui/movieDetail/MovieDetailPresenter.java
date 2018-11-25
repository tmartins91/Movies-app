package com.tmartins.task.moviesapp.ui.movieDetail;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;

import com.tmartins.task.moviesapp.core.datasource.MoviesRepository;
import com.tmartins.task.moviesapp.core.model.CollectionDetails;
import com.tmartins.task.moviesapp.core.model.Movie;
import com.tmartins.task.moviesapp.helpers.schedulers.RunOn;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.tmartins.task.moviesapp.helpers.schedulers.SchedulerType.IO;
import static com.tmartins.task.moviesapp.helpers.schedulers.SchedulerType.UI;

public class MovieDetailPresenter implements MovieDetailContract.Presenter, LifecycleObserver {

    private MoviesRepository repository;

    private MovieDetailContract.View view;

    private Scheduler ioScheduler;
    private Scheduler uiScheduler;

    private CompositeDisposable disposeBag;

    @Inject
    public MovieDetailPresenter(MoviesRepository repository, MovieDetailContract.View view,
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
    public void onAttach() { }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onDetach() {
        disposeBag.clear();
    }

    @Override
    public void loadMovieDetail(long id) {
        Disposable disposable = repository.loadMovieDetails(id)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe(
                        this::handleReturnedData,
                        this::handleError,
                        () -> view.stopLoadingIndicator());

        disposeBag.add(disposable);
    }

    @Override
    public void loadCollectionDetails(long id) {
        Disposable disposable = repository.loadMovieCollections(id)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe(
                        this::handleCollectionReturnedData,
                        this::handleError,
                        () -> view.stopLoadingIndicator());

        disposeBag.add(disposable);
    }

    private void handleReturnedData(Movie movie) {
        view.stopLoadingIndicator();
        if (movie != null) {
            view.showMovieDetail(movie);
        } else {
            view.showErrorMessage("No data returned");
        }
    }

    private void handleCollectionReturnedData(CollectionDetails collectionDetails) {
        if (collectionDetails != null) {
            view.showCollectionDetails(collectionDetails);
        } else {
            view.showErrorMessage("No data returned");
        }
    }

    private void handleError(Throwable error) {
        view.stopLoadingIndicator();
        view.showErrorMessage(error.getLocalizedMessage());
    }

}
