package com.example.practica1;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class NotificationWork extends Worker {
    private static final String TAG = "NotificationWork";
    public static final String NOTI_MGR = "NOTI_NGR";
    public static final String BUILDER = "BUILDER";
    //public static NotificationManagerCompat notificationManager;
    //public static NotificationCompat.Builder builder;

    public NotificationWork(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        //NotificationManagerCompat notificationManager = getInputData().getInt(NOTI_MGR, 0);
        //notificationManager.notify(1, builder.build()); //notificationId
        Log.i("Cosa ","aaaaaaaaaaaaaaaa");
        //Log.e(TAG, "doWork: Work is done.");
        return Result.success();
    }
}
