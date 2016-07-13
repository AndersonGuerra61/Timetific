package com.andersonguerra.org.timetablestudentapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MenuPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_menu_principal, menu);
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
    public void goToSettings (View view){
        Intent intent = new Intent (this, Settings.class);
        startActivity(intent);
    }
    public void goToHomework (View view){
        Intent intent = new Intent (this, Homework.class);
        startActivity(intent);
    }
    public void goToTimetable (View view) {
        Intent intent = new Intent(this, Horario.class);
        startActivity(intent);
    }
}
