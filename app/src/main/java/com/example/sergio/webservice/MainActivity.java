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

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private final static String IP = "192.168.0.102";
    private final static String ENDPOINT =  "http://"+IP+"/RestAndroidExample/public/cars";
    private  final  String DEBUGTAG ="@MainActivity";
    private EditText  edId;
    private EditText edMarca;
    private Button[] botones;
    private TextView txtResultado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.iniciarElementos();
        this.iniciarListener();
    }

    private void iniciarListener(){
        View.OnClickListener l= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnCrearRegistro:
                        Building.getBuildings(new DataReadyListener() {
                            @Override
                            public void onSuccess(List objects) {

                            }

                            @Override
                            public void onError(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                Toast.makeText(MainActivity.this,responseString,Toast.LENGTH_LONG).show();
                            }
                        });
//                        rearRegistro();
                        break;
                    case R.id.btnLeerRegistro:
//                        leerRegistros();
                        break;
                    case R.id.btnActualizarRegistro:
//                        actualizarRegistro();
                        break;
                    case R.id.btnBorrarRegistro:
//                        borrarRegistro();
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
/*
    private void crearRegistro(){
        RequestParams rp = new RequestParams();
        rp.put("brand", edMarca.getText().toString());
AsyncHttpClient a = new AsyncHttpClient();
        a.a
        new AsyncHttpClient().post(ENDPOINT,rp,
                new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                        Log.d(DEBUGTAG, response.toString());
                        leerRegistros();
                    }
                });
    }

    private void leerRegistros(){
        new AsyncHttpClient().get(ENDPOINT, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                List<Car> carList = new ArrayList<>();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObjCar = response.getJSONObject(i);
                        Car car = new Car(jsonObjCar);
                        carList.add(car);
                    }
                    final StringBuilder output = new StringBuilder("");
                    for (Car car : carList) {
                        output.append("id: ")
                                .append(car.getId())
                                .append(" - ")
                                .append(car.getBrand())
                                .append('\n');
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            txtResultado.setText(output.toString());
                        }
                    });

                } catch (JSONException e) {
                    Log.e(DEBUGTAG, e.getMessage());
                }
            }
        });
    }

    private void actualizarRegistro(){
        String url = new StringBuilder(ENDPOINT)
                .append('/')
                .append(edId.getText())
                .toString();

        RequestParams rp = new RequestParams();
        rp.put("brand", this.edMarca.getText().toString());
        new AsyncHttpClient().put(url, rp,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Log.d(DEBUGTAG, response.toString());
                        leerRegistros();
                    }
                });
    }

    private void borrarRegistro(){
        String url = new StringBuilder(ENDPOINT)
                .append('/')
                .append(edId.getText().toString()).toString();

        new AsyncHttpClient().delete(url,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                Log.d(DEBUGTAG, response.toString());
                leerRegistros();
            }
        });
    }*/

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
