package com.andersonguerra.org.timetablestudentapp.AlarmHelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Anderson Guerra on 3/30/2016.
 */
public class Alarm_Reciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("Estamos en el reciever.", "Yay!");

        String get_your_string = intent.getExtras().getString("extra");
        Log.e("Esta es una llave?", get_your_string);

        Intent service_intent = new Intent(context, RingtonePlayingService.class);

        service_intent.putExtra("extra", get_your_string);
        context.startService(service_intent);

    }
}
