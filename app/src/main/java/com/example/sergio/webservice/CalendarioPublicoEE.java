package com.example.sergio.webservice.VistasPublicasE;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.sergio.webservice.R;

/**
 * Created by INIFAP on 10/12/2015.
 */
public class CalendarioPublicoEE extends AppCompatActivity {

    CalendarView calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario_publico_ee);

        calendar = (CalendarView) findViewById(R.id.calendarView);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
             @Override
             public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                 Toast.makeText(getApplicationContext(), dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
             }
     }


        );

    }

}
