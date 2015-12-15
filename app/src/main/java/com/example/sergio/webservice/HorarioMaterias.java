package com.example.sergio.webservice;

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

import java.util.List;
import cz.msebera.android.httpclient.Header;

public class HorarioMaterias extends AppCompatActivity {

    private String[] semana;
    private String[] horario;
    private Button btnfunciona;
    String[][] matriz = new String[10][10];
    TableLayout table_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horario_materias);


        semana = getResources().getStringArray(R.array.cabecera_tabla);
        horario = getResources().getStringArray(R.array.horario);

        table_layout = (TableLayout) findViewById(R.id.tableLayout1);
        btnfunciona=(Button) findViewById(R.id.btnFunciona);
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




            }






    private void iniciarListener() {
       btnfunciona.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               switch (v.getId()) {

                   case R.id.btnFunciona:
                       Schedule.getSchedules(new DataReadyListener() {
                           @Override
                           public void onSuccess(List objects) {
                               for (int j = 0; j < 10; j++) {

                                   for (int i = 0; i < 7; i++) {
                                       Schedule schedule = (Schedule) objects.get(i);
                                       String materia = schedule.subjectName;
                                       Log.i("MAO","Semana:"+semana[i]);
                                       Log.i("MAO","weekday:"+schedule.weekday);

                                       Log.i("MAO","tiempo:"+horario[j]);
                                       Log.i("MAO","tiempoSchedule:"+schedule.time);

                                       if ((semana[i].equals(schedule.weekday)) && (horario[j].equals(schedule.time))) {
                                           Log.i("MAO","Entró:"+schedule.subjectName);
                                           matriz[j + 2][i + 1] = materia;
                                           Log.i("MAO","Entró:"+matriz[j+2][i+1]);
                                       }
                                   }
                               }
                               ContruirTabla(9, 7);
                               Toast.makeText(HorarioMaterias.this, "Datos obtenidos de horario de clases", Toast.LENGTH_LONG).show();
                           }

                           @Override
                           public void onNoNetwork(List objects) {

                           }

                           @Override
                           public void onError(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                               Toast.makeText(HorarioMaterias.this, responseString, Toast.LENGTH_LONG).show();
                           }

                       });
               }
           }
       });
    }


        public void ContruirTabla(int rows, int cols){

            for (int i = 1; i <= rows; i++) {
                TableRow row = new TableRow(this);
                row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));

                // inner for loop
                for (int j = 1; j <= cols; j++) {

                    TextView tv = new TextView(this);
                    tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                    tv.setTextSize(6);

                    tv.setBackgroundResource(R.drawable.cell_shape);
                    tv.setPadding(20, 20, 20, 20);

                    if(i == 1){
                        if(j==1){
                            tv.setText("");
                            tv.setBackgroundColor(000000);
                        }else {
                            tv = setColorLable(tv);
                            tv.setText(semana[j - 2]);
                        }
                    }else {
                        if(j==1){
                            tv = setColorLable(tv);
                            tv.setText(horario[i - 2]);
                        }else {

                            if(matriz==null){
                                Log.i("MAo","Entró a nula la matrz");
                                tv.setText(":(");
                            }else{
                                Log.i("MAo","Estállenando");
                                tv.setText(matriz[i][j]);
                                Log.i("MAO","Datos:"+matriz[i][j]);
                            }
                        }
                    }
                    final float scale = getResources().getDisplayMetrics().density;

                    int trHeight = (int) (50 * scale + 0.5f);
                    int trWidth = (int) (10 * scale + 0.5f);
                    row.addView(tv,new TableRow.LayoutParams(trWidth, trHeight));

                }
                table_layout.addView(row);
            }

        }




    private void iniciarElementos(){

    }

    private TextView setColorLable(TextView tv){
        tv.setBackgroundColor(0xFF006699);
        tv.setTextColor(Color.WHITE);
        return tv;
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


