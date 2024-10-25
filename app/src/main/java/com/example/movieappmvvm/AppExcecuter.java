package com.example.movieappmvvm;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExcecuter {
    // singleton
    private static AppExcecuter instance;

    public static AppExcecuter getInstance() {
        if(instance == null) {
            instance = new AppExcecuter();
        }
        return instance;
    }

    private final ScheduledExecutorService mNetWorkIO = Executors.newScheduledThreadPool(3);

    public ScheduledExecutorService networkIO() {
        return mNetWorkIO;
    }
}
