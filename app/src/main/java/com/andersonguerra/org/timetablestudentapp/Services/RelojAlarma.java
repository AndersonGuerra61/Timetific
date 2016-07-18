package com.andersonguerra.org.timetablestudentapp.Services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.andersonguerra.org.timetablestudentapp.AlarmHelper.Alarm_Reciever;
import com.andersonguerra.org.timetablestudentapp.R;

import java.util.Calendar;

public class RelojAlarma extends AppCompatActivity {

    // variables para la alarma
    AlarmManager alarm_manager;
    TimePicker alarm_timepicker;
    TextView update_text;
    Context context;
    PendingIntent pending_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reloj_alarma);
        this.context = this ;

        // inicializar nuestro AlarmManager
        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // inicializar nuestro timepicker
        alarm_timepicker = (TimePicker) findViewById(R.id.timePicker);

        // inicializar text update box
        update_text = (TextView) findViewById(R.id.update_text);

        // creando una instancia final del calendario
        final Calendar calendar = Calendar.getInstance();

        // creando el intent a la clase Alarm Receiver
        final Intent my_intent = new Intent (this.context, Alarm_Reciever.class);

        // iniciar el boton de inicio
        Button alarm_on = (Button) findViewById(R.id.alarm_on);
        // Crenado el onClick listener para iniciar la alarma
        alarm_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar.set(Calendar.HOUR_OF_DAY, alarm_timepicker.getHour());
                calendar.set(Calendar.MINUTE, alarm_timepicker.getMinute());

                int hour = alarm_timepicker.getHour();
                int minute = alarm_timepicker.getMinute();

                String hour_string = String.valueOf(hour);
                String minute_string = String.valueOf(minute);

                if (hour > 12) {
                    //convieriendo las horas a minutos
                    hour_string = String.valueOf(hour - 12);
                }

                if (minute < 10) {
                    //05:5 --> 05:05
                    minute_string = "0" + String.valueOf(minute);
                }

                // metodo para cambiar el textp
                set_alarm_text("La alarma sonara a las: " + hour_string + ":" + minute_string);

                my_intent.putExtra("extra", "alarma encendida");

                pending_intent = PendingIntent.getBroadcast(RelojAlarma.this, 0, my_intent, PendingIntent.FLAG_UPDATE_CURRENT);

                alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        pending_intent);
            }
        });

        //initialise stop button
        Button alarm_off =(Button) findViewById(R.id.alarm_off);
        //create onClick listener to stop alarm

        alarm_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //method that changes the update text
                set_alarm_text("Alarma apagada!");
                //cancels the alarm
                alarm_manager.cancel(pending_intent);

                //put extra string into my_intent
                //tells clock I pressed "alarm off" button
                my_intent.putExtra("extra", "alarma apagada");


                //stop the ringtone
                sendBroadcast(my_intent);
            }
        });
    }

    private void set_alarm_text(String output) {
        update_text.setText(output);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_alarm_clock, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
