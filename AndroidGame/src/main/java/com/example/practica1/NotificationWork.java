package com.example.practica1;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class NotificationWork extends Worker {
    private static final String TAG = "NotificationWork";

    public NotificationWork(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.i("Cosa ","aaaaaaaaaaaaaaaa");
        Log.e(TAG, "doWork: Work is done.");
        return Result.success();
    }
}
