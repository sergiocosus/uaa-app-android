package com.example.sergio.webservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.sergio.webservice.CalendarioPublicoEE;

public class MenuPrincipal extends AppCompatActivity implements View.OnClickListener{
    private Button btncalendario,btnhistoria,btnoferta,btngaleria,btnmapa,btnexamenes,btnhorario;
    String VariableBanderaLog="Hola";
    private ImageButton btnLog;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

         VariableBanderaLog = getIntent().getStringExtra("variableBanderaLog");
        Log.i("Mao", "Cadena2" + VariableBanderaLog);
        if(VariableBanderaLog==null){
            VariableBanderaLog="NoLogueado";
        }


      this.iniciarElementos();
        if(VariableBanderaLog.equals("Logueado")){
            Log.i("Mao", "entró" + VariableBanderaLog);
            btnexamenes.setVisibility(View.VISIBLE);
            btnhorario.setVisibility(View.VISIBLE);
            btnmapa.setVisibility(View.VISIBLE);
            btnLog.setVisibility(View.GONE);



        }
    }

    public void iniciarElementos() {

        btncalendario=(Button) findViewById(R.id.btnCalendario);
        btnhistoria=(Button) findViewById(R.id.btnHistoria);
        btnoferta=(Button) findViewById(R.id.btnOferta);
        btngaleria=(Button) findViewById(R.id.btnGaleria);
        btnexamenes=(Button) findViewById(R.id.btnExamenes);
        btnmapa=(Button) findViewById(R.id.btnMapa);
        btnhorario=(Button) findViewById(R.id.btnHorario);
        btnLog=(ImageButton)findViewById(R.id.btnUsuario);

        btnLog.setOnClickListener(this);
        btncalendario.setOnClickListener(this);
        btnhistoria.setOnClickListener(this);
        btnoferta.setOnClickListener(this);
        btngaleria.setOnClickListener(this);
        btnmapa.setOnClickListener(this);
        btnexamenes.setOnClickListener(this);


    }


    public void onClick(View v){
        int id= v.getId();
        if(id==R.id.btnHistoria){
            Intent ventana = new Intent();
            ventana.setClass(getApplicationContext(),HistoriaE.class);
            startActivity(ventana);

        }
        if(id==R.id.btnCalendario){

            Intent ventana = new Intent();
            ventana.setClass(getApplicationContext(), CalendarioPublicoEE.class);
            startActivity(ventana);

        }

        if(id==R.id.btnOferta){
            Intent ventana = new Intent();
            ventana.setClass(getApplicationContext(), PlanesEstudioE.class);
            startActivity(ventana);

        }
        if(id==R.id.btnGaleria){

            Intent ventana = new Intent();
            ventana.setClass(getApplicationContext(),GaleriaImagE.class);
            startActivity(ventana);

        }

        if(id==R.id.btnUsuario){
            LoginMetodo();
        }
        if(id==R.id.btnMapa){
            Intent ventana = new Intent();
            ventana.setClass(getApplicationContext(),ActivityMaps.class);
            startActivity(ventana);
        }



    }

    public void LoginMetodo(){
        Intent ventana = new Intent();
        ventana.setClass(getApplicationContext(), LoginActivity.class);
        startActivity(ventana);

    }


}
