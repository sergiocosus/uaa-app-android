package com.example.sergio.webservice;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sergio.webservice.Services.Auth;
import com.example.sergio.webservice.Services.DataReadyListener;
import com.example.sergio.webservice.Services.Schedule;
import com.example.sergio.webservice.Services.WebService;


import com.example.sergio.webservice.Services.Auth;
import com.example.sergio.webservice.Services.DataReadyListener;
import com.example.sergio.webservice.Services.ExamSchedule;
import cz.msebera.android.httpclient.Header;

import java.util.ArrayList;
import java.util.List;

public class CalendarioAlternativo extends AppCompatActivity {
    private Button btnlista;
    private ListView lista1;
    private List<String> ArrayExam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario_alternativo);
        btnlista = (Button) findViewById(R.id.btnLista);
        lista1=(ListView) findViewById(R.id.listView);
        WebService.setContext(this);
        Auth.login("150795", "070993", new DataReadyListener() {
            @Override
            public void onSuccess(List objects) {
                iniciarListener();
            }

            @Override
            public void onNoNetwork(List objects) {

            }

            @Override
            public void onError(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }
        });


        iniciarListener();


    }

    private void iniciarListener() {
        btnlista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {


                    case R.id.btnLista:


                        ExamSchedule.getExamSchedules(new DataReadyListener() {
                            @Override
                            public void onSuccess(List objects) {
                                ArrayExam = new ArrayList<String>();
                                for(int i=0;i<objects.size();i++){
                                ExamSchedule examSchedule = (ExamSchedule) objects.get(i);

                                ArrayExam.add(examSchedule.subjectName);


                                }
                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CalendarioAlternativo.this,android.R.layout.simple_list_item_1,ArrayExam);

                                lista1.setAdapter(arrayAdapter);


                            }

                            @Override
                            public void onError(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                Toast.makeText(CalendarioAlternativo.this, responseString, Toast.LENGTH_LONG).show();


                            }

                            @Override
                            public void onNoNetwork(List objects) {

                            }
                        });

                }
            }
});
    }}
