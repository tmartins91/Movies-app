package com.tmartins.task.moviesapp.helpers.schedulers;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface RunOn {

    SchedulerType value() default SchedulerType.IO;

}
