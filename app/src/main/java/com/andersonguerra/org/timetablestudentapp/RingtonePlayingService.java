package com.andersonguerra.org.timetablestudentapp;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class RingtonePlayingService extends Service {

    MediaPlayer media_song;
    int startId;
    boolean isRunning;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        String state = intent.getExtras().getString("extra");
        Log.e("Ringtone extra is", state);

        NotificationManager notify_manager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        Intent intent_alarm_clock = new Intent(this.getApplicationContext(), RelojAlarma.class);
        PendingIntent pending_intent_alarm_clock = PendingIntent.getActivity(this, 0,
                intent_alarm_clock, 0);

        Notification notification_popup = new Notification.Builder (this)
                .setContentTitle("Notificacion de la alarma!")
                .setContentText("Clickeame!")
                .setContentIntent(pending_intent_alarm_clock)
                .setSmallIcon(R.drawable.notification_icon)
                .setAutoCancel(true)
                .build();

        notify_manager.notify(0, notification_popup);

        assert state != null;
        switch (state) {
            case "alarm on":
                startId = 1;
                break;
            case "alarm off":
                startId = 0;
                break;
            default:
                startId = 0;
                break;
        }

        if (!this.isRunning && startId == 1) {
            Log.e("there is no music, ", "and you want start");
            media_song = MediaPlayer.create(this, R.raw.iphone_alarm);
            media_song.start();
            this.isRunning = true;
            this.startId = 0;
        }

        else if(this.isRunning && startId == 0){
            Log.e("there is music, ", "and you want end");

            media_song.stop();;
            media_song.reset();

            this.isRunning = false;
            this.startId = 0;
        }

        else if (!this.isRunning && startId == 0){
            Log.e("there is no music, ", "and you want end");
            this.isRunning = false;
            this.startId = 0;
        }

        else if(this.isRunning && startId == 1){
            Log.e("there is music, ", "and you want end");
            this.isRunning = true;
            this.startId = 1;
        }
        else {
            Log.e("else", "somehow you reach this");

        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.e("on Destroy called", "ta da");

        super.onDestroy();
        this.isRunning = false;
    }
}
