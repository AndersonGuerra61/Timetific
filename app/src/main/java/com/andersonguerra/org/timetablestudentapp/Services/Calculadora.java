package com.andersonguerra.org.timetablestudentapp.Services;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.andersonguerra.org.timetablestudentapp.R;

public class Calculadora extends AppCompatActivity {

    String total ="";
    double v1,v2;
    String sign="";
    private View v;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);
    }

    public void OnClick (View v){
        this.v = v;
        Button button = (Button)v;
        String str=button.getText().toString();
        total+=str;
        EditText edit=(EditText)findViewById(R.id.editText3);
        edit.setText(total);

    }

    public void OnAdd (View v){
        v1=Double.parseDouble(total);
        total="";
        Button button = (Button)v;
        String str = button.getText().toString();
        sign = str;
        EditText edit = (EditText)findViewById(R.id.editText3);
        edit.setText("");
    }
    public void  OnCalculate(View v){
        EditText edit = (EditText)findViewById(R.id.editText3);
        String str2=edit.getText().toString();
        v2=Double.parseDouble(str2);
        double grand_total=0;
        if (sign.equals("+")){
            grand_total=v1+v2;}
        else if (sign.equals("-")) {
            grand_total=v1-v2;
        }
        else if(sign.equals("x")){
            grand_total=v1*v2;
        }
        else if(sign.equals("/")){
            grand_total=v1/v2;
        }

        edit.setText(grand_total+"");
    }
    public void OnClear(View v){
        EditText edit = (EditText)findViewById(R.id.editText3);
        edit.setText("");
        total="";
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_calculadora, menu);
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
