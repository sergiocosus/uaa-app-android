package com.example.sergio.webservice;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;


public class GaleriaImagE extends ActionBarActivity implements View.OnClickListener {

    Button btnsiguiente, btnanterior;
    TextView textview;
    ImageView imagen;
    int[] fotoId={
            R.mipmap.ccbas,
            R.mipmap.ayotz,
            R.mipmap.estadio,
            R.mipmap.uaamain

    };
    int i=0;
    int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria_imag_e);
        btnsiguiente=(Button) findViewById(R.id.btnSiguiente);
        btnanterior=(Button) findViewById(R.id.btnAnterior);

        imagen = (ImageView) findViewById(R.id.imageView);

        btnanterior.setOnClickListener(this);
        btnsiguiente.setOnClickListener(this);




        total=fotoId.length;

    }





    public void onClick(View v){
        int id= v.getId();
        if(id==R.id.btnSiguiente){
            i++;
            if(i==total) i=0;
        }
        if(id==R.id.btnAnterior){
            i--;
            if(i==-1) i=total-1;
        }
        imagen.setImageResource(fotoId[i]);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calendario_publico_ee, menu);
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
