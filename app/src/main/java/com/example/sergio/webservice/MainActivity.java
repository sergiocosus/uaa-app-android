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
    private EditText  edId;
    private EditText edMarca;
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
       /* AcademicCalendarSQLite sqLite = new AcademicCalendarSQLite(this,"app.db",null,1);

        Log.d("DATABASE", ( sqLite.getAll().get(0)).name);
*/
        View.OnClickListener l= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnCrearRegistro:
                        Auth.logout(new DataReadyListener() {
                            @Override
                            public void onSuccess(List objects) {
                                Log.i(DEBUGTAG,"Deslogueado!");
                            }

                            @Override
                            public void onError(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                Toast.makeText(MainActivity.this, responseString, Toast.LENGTH_LONG).show();
                            }
                        });
//
                        break;
                    case R.id.btnLeerRegistro:
                        Auth.login("150795", "070993", new DataReadyListener() {
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
                    case R.id.btnActualizarRegistro:
                        ExamSchedule.getExamSchedules(new DataReadyListener() {
                            @Override
                            public void onSuccess(List objects) {

                                Toast.makeText(MainActivity.this, "Datos obtenidos de Horario de Examnes", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onError(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                Toast.makeText(MainActivity.this, responseString, Toast.LENGTH_LONG).show();

                            }
                        });

                        break;
                    case R.id.btnBorrarRegistro:
                        Schedule.getSchedules(new DataReadyListener() {
                            @Override
                            public void onSuccess(List objects) {
                                Toast.makeText(MainActivity.this, "Datos obtenidos de Horario de Horarios de clases", Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onError(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                Toast.makeText(MainActivity.this, responseString, Toast.LENGTH_LONG).show();

                            }
                        });
                        break;
                }
            }
        };

        for(Button b : botones){
            b.setOnClickListener(l);
        }
    }

    private void iniciarElementos(){
        edId = (EditText) findViewById(R.id.edid);
        edMarca =(EditText) findViewById(R.id.edmarca);
        txtResultado =(TextView) findViewById(R.id.txtResultado);

        botones = new Button[]{
                (Button) findViewById(R.id.btnCrearRegistro),
                (Button) findViewById(R.id.btnLeerRegistro),
                (Button) findViewById(R.id.btnActualizarRegistro),
                (Button) findViewById(R.id.btnBorrarRegistro)
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
