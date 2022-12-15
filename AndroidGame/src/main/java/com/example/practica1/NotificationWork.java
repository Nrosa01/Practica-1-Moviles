package com.example.practica1;



import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class NotificationWork extends Worker {
    private static final String TAG = "NotificationWork";

    private NotificationCompat.Builder builder;
    private NotificationManagerCompat notificationManager;
    //public static NotificationManagerCompat notificationManager;
    //public static NotificationCompat.Builder builder;

    public NotificationWork(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);

        //----------------------------------------------------------------------
        createNotificationChannel();

        // CREAR INTENT: Create an explicit intent for an Activity in your app
        Intent intent = new Intent(getApplicationContext(), MainActivity.class); //triple
        intent.putExtra("someKey", 115);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);

        //CREAR NOTIFICACION:
        //var CHANNEL_ID = "My Notification";
        builder = new NotificationCompat.Builder(getApplicationContext(),  "My Notification") //CHANNEL_ID
                .setSmallIcon(R.drawable.bell) //R.drawable.notification_icon
                .setContentTitle("Notificación del Nonograma")
                .setContentText("Eres un grande!")
                // Si quieres que ocupe mas de 1 linea la notificacion
                //.setStyle(NotificationCompat.BigTextStyle()
                //      .bigText("Much longer text that cannot fit one line..."))
                //-----------------
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        notificationManager = NotificationManagerCompat.from(getApplicationContext());
        //MOSTRAR NOTIFICACION--------------------------------------------------
        // notificationId is a unique int for each notification that you must define

    }

    @NonNull
    @Override
    public Result doWork() {
        //NotificationManagerCompat notificationManager = getInputData().getInt(NOTI_MGR, 0);
        //notificationManager.notify(1, builder.build()); //notificationId
        Log.i("Cosa ","aaaaaaaaaaaaaaaa");
        notificationManager.notify(1, builder.build()); //notificationId
        //Log.e(TAG, "doWork: Work is done.");
        return Result.success();
    }

    //CREAR CANAL ==> si quieres meter sonidos y mierdas:
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "My Notification";//getString(R.string.channel_name);
            String description = "My Notification Description";//getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("My Notification", name, importance); //CHANNEL_ID
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getApplicationContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
