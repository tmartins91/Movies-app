package com.tmartins.task.moviesapp.helpers.schedulers;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.tmartins.task.moviesapp.helpers.schedulers.SchedulerType.IO;
import static com.tmartins.task.moviesapp.helpers.schedulers.SchedulerType.UI;

@Module
public class SchedulerModule {

    @Provides
    @RunOn(IO)
    Scheduler provideIo(){
        return Schedulers.io();
    }

    @Provides
    @RunOn(UI)
    Scheduler provideUi() {
        return AndroidSchedulers.mainThread();
    }

}
