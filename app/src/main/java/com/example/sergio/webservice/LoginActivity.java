package com.example.sergio.webservice;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sergio.webservice.Services.Auth;
import com.example.sergio.webservice.Services.DataReadyListener;
import com.example.sergio.webservice.Services.WebService;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{
    private CheckBox recordar;
    private EditText id_input;
    private EditText password_input;
    private TextView txtResultado;
    private String banderaLog="";
    //private Button[] botones;
    private ImageButton btnLogin;
    private Button btnIngresar,boton,btnpublico;

    private FragmentManager fragmentManager;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        WebService.setContext(this);


        this.iniciarElementos();
        this.iniciarListeners();
      //  this.cargarPreferencias();
    }

    public void iniciarListeners() {

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ingresar();
            }
        });

        btnpublico.setOnClickListener(new View.OnClickListener()
        {
                                          @Override
                                          public void onClick(View view) {
                                              banderaLog="";
                                              Intent ventana = new Intent();
                                              ventana.putExtra("variableBanderaLog", banderaLog);
                                              ventana.setClass(getApplicationContext(), MenuPrincipal.class);
                                              startActivity(ventana);
                                          }

                                      }

        );
    }
  /*      boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarPreferencias();
            }
        });
    } */

    public void iniciarElementos() {
        id_input = (EditText) findViewById(R.id.id_input);
        password_input = (EditText) findViewById(R.id.password_input);
        txtResultado = (TextView) findViewById(R.id.TextoResultado);
        btnIngresar = (Button) findViewById(R.id.BotonIngresar);
        recordar = (CheckBox) findViewById(R.id.Recordar);
        btnpublico=(Button) findViewById(R.id.btnPublico);
        //  this.btnLogin = (ImageButton) findViewById(R.id.btn_Login);


        //  fragmentManager = getSupportFragmentManager();
    //  prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
     //   editor = prefs.edit();
     //   editor.commit();
    }


        public void Ingresar () {
            Auth.login(id_input.getText().toString(), password_input.getText().toString(), new DataReadyListener() {
                @Override
                public void onSuccess(List objects) {
                    Toast.makeText(LoginActivity.this, "Se pudo Loguear Correctamente", Toast.LENGTH_LONG).show();
                    Log.i("Mao", "Sepudo Loguear Correctamente!!!!!!");
                    banderaLog="Logueado";
                    Intent ventana = new Intent();
                    ventana.putExtra("variableBanderaLog", banderaLog);
                    ventana.setClass(getApplicationContext(), MenuPrincipal.class);
                    startActivity(ventana);

                }

                @Override
                public void onError(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Toast.makeText(LoginActivity.this, "Datos Incorrectos, intente nuevamente", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onNoNetwork(List objects) {

                }
            });
        }


    public void cargarPreferencias() {
        id_input.setText(prefs.getString("id", ""));
        password_input.setText(prefs.getString("password", ""));
    }

    public void guardarPreferencias() {
        String nombre = id_input.getText().toString();
        String pass = password_input.getText().toString();
        editor.putString("id", nombre);
        editor.putString("password", pass);
        editor.commit();
    }

    public boolean getRecordar(){
        return recordar.isChecked();
    }



    public void setDatos(String id, String pass, String recordar){
        id_input.setText(id);
        password_input.setText(pass);
        if(recordar.equals("si")) {
            this.recordar.setChecked(true);
        }else{
            this.recordar.setChecked(false);
        }
    }
}







