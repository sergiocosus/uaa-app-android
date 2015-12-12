package com.example.sergio.webservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sergio.webservice.Database.AcademicCalendarSQLite;
import com.example.sergio.webservice.Database.BuildingSQLite;
import com.example.sergio.webservice.Database.ExamScheduleSQLite;
import com.example.sergio.webservice.Database.OfferSQLite;
import com.example.sergio.webservice.Database.ScheduleSQLite;
import com.example.sergio.webservice.Services.AcademicCalendar;
import com.example.sergio.webservice.Services.Auth;
import com.example.sergio.webservice.Services.Building;
import com.example.sergio.webservice.Services.DataReadyListener;
import com.example.sergio.webservice.Services.ExamSchedule;
import com.example.sergio.webservice.Services.Offer;
import com.example.sergio.webservice.Services.Schedule;
import com.example.sergio.webservice.Services.WebService;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private  final  String DEBUGTAG ="@MainActivity";
    private EditText  id;
    private EditText password;
    private Button[] botones;
    private TextView txtResultado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebService.setContext(this);



        this.iniciarElementos();
        this.iniciarListener();
    }

    private void iniciarListener(){
        View.OnClickListener l= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnCrearRegistro:
                        Auth.login(id.getText().toString(), password.getText().toString(), new DataReadyListener() {
                            @Override
                            public void onSuccess(List objects) {
                                Toast.makeText(MainActivity.this, "Logeadoooo", Toast.LENGTH_LONG).show();
                                Log.i("Log?", "logeado!!!!!!");
                            }

                            @Override
                            public void onError(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                Toast.makeText(MainActivity.this, responseString, Toast.LENGTH_LONG).show();
                            }
                        });
                        break;

                    case R.id.btnLeerRegistro:
                        Auth.logout(new DataReadyListener() {
                            @Override
                            public void onSuccess(List objects) {
                                Log.i(DEBUGTAG, "Deslogueado!");
                            }

                            @Override
                            public void onError(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                Toast.makeText(MainActivity.this, responseString, Toast.LENGTH_LONG).show();
                            }
                        });
                        break;

                    case R.id.btnAcademicCalendar:
                        AcademicCalendar.getAcademicCalendar(new DataReadyListener() {
                            @Override
                            public void onSuccess(List objects) {
                                AcademicCalendar academicCalendar = (AcademicCalendar) objects.get(0);
                                Toast.makeText(MainActivity.this, academicCalendar.name, Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onError(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                Toast.makeText(MainActivity.this, responseString, Toast.LENGTH_LONG).show();
                                AcademicCalendarSQLite academicCalendar = new AcademicCalendarSQLite(MainActivity.this,WebService.database,null,1);
                                List academics = academicCalendar.getAll();
                                for(int i =0; i<academics.size(); i++){
                                    ((AcademicCalendar)academics.get(i)).log();
                                }

                            }
                        });

                        break;
                    case R.id.btnBuildings:
                        Building.getBuildings(new DataReadyListener() {
                            @Override
                            public void onSuccess(List objects) {
                                Toast.makeText(MainActivity.this, "Datos obtenidos de ubicaciones", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onError(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                Toast.makeText(MainActivity.this, responseString, Toast.LENGTH_LONG).show();
                                BuildingSQLite buildingSQLite = new BuildingSQLite(MainActivity.this,WebService.database,null,1);
                                List builings = buildingSQLite.getAll();
                                for(int i =0; i<builings.size(); i++){
                                    ((Building)builings.get(i)).log();
                                }
                            }
                        });
                        break;
                    case R.id.btnExamSchedule:
                        ExamSchedule.getExamSchedules(new DataReadyListener() {
                            @Override
                            public void onSuccess(List objects) {
                                Toast.makeText(MainActivity.this, "Datos obtenidos de Promacacion de examenes", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onError(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                Toast.makeText(MainActivity.this, responseString, Toast.LENGTH_LONG).show();
                                ExamScheduleSQLite examScheduleSQLite = new ExamScheduleSQLite(MainActivity.this,WebService.database,null,1);
                                List builings = examScheduleSQLite.getAll();
                                for(int i =0; i<builings.size(); i++){
                                    ((ExamSchedule)builings.get(i)).log();
                                }
                            }
                        });
                        break;
                    case R.id.btnOffer:
                        Offer.getOffers(new DataReadyListener() {
                            @Override
                            public void onSuccess(List objects) {
                                Toast.makeText(MainActivity.this, "Datos obtenidos de Oferta", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onError(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                Toast.makeText(MainActivity.this, responseString, Toast.LENGTH_LONG).show();
                                OfferSQLite offerSQLite = new OfferSQLite(MainActivity.this,WebService.database,null,1);
                                List builings = offerSQLite.getAll();
                                for(int i =0; i<builings.size(); i++){
                                    ((Offer)builings.get(i)).log();
                                }
                            }
                        });
                        break;
                    case R.id.btnSchedule:
                        Schedule.getSchedules(new DataReadyListener() {
                            @Override
                            public void onSuccess(List objects) {
                                Toast.makeText(MainActivity.this, "Datos obtenidos de horario de clases", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onError(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                Toast.makeText(MainActivity.this, responseString, Toast.LENGTH_LONG).show();
                                ScheduleSQLite scheduleSQLite = new ScheduleSQLite(MainActivity.this,WebService.database,null,1);
                                List builings = scheduleSQLite.getAll();
                                for(int i =0; i<builings.size(); i++){
                                    ((Schedule)builings.get(i)).log();
                                }
                            }
                        });
                }
            }
        };

        for(Button b : botones){
            b.setOnClickListener(l);
        }
    }

    private void iniciarElementos(){
        id = (EditText) findViewById(R.id.txtId);
        password =(EditText) findViewById(R.id.txtpassword);

        botones = new Button[]{
                (Button) findViewById(R.id.btnCrearRegistro),
                (Button) findViewById(R.id.btnLeerRegistro),
                (Button) findViewById(R.id.btnAcademicCalendar),
                (Button) findViewById(R.id.btnBuildings),
                (Button) findViewById(R.id.btnExamSchedule),
                (Button) findViewById(R.id.btnOffer),
                (Button) findViewById(R.id.btnSchedule)
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
