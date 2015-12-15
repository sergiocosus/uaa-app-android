package com.example.sergio.webservice;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CalendarioN extends AppCompatActivity implements CalendarView.OnDateChangeListener {
    private TextView text;
    private CalendarView cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario_n);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        cal=(CalendarView)findViewById(R.id.calendarView2);

        text=(TextView)findViewById(R.id.textView5);
        cal.setOnDateChangeListener(this);

    }


    @Override
    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
        Date fechaActual=new Date();
        String fechas=fechaActual.getDay()+"/"+fechaActual.getMonth()+"/"+2015;
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        month=month+1;
        String fecha=dayOfMonth+"/"+month+"/"+year;
        Date fechaDate = null;
        Date fechaA=null;
        try {
            fechaDate = formato.parse(fecha);
            fechaA=formato.parse(fechas);
        }
        catch (ParseException ex)
        {
            System.out.println(ex);
        }

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(fechaDate);
        int dia=cal.get(Calendar.DAY_OF_WEEK);
        GregorianCalendar cal2=new GregorianCalendar();
        cal2.setTime(fechaA);
        int i=fechaA.compareTo(fechaDate);
        if(i<=0){
            if(month==12&&dayOfMonth==25||month==12&&dayOfMonth==24||month==1&&dayOfMonth==1||month==12&&dayOfMonth==31){
                text.setText("Horario no disponible");
            }else{
                if(dia==2||dia==1||dia>=6){
                    text.setText("Horario de 6:00 pm. a 10:00 pm.");

                }else{
                    text.setText("Horario no disponible");

                }
            }
        }else{
            text.setText("Horario no disponible");
        }
    }
}