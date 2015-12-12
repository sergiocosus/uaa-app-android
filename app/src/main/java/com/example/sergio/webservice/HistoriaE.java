package com.example.sergio.webservice;

import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class HistoriaE extends AppCompatActivity {
   TextView textview;
   ImageView imagen;
    int[] fotoId={
            R.mipmap.casa};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historia_e);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textview=(TextView) findViewById(R.id.textView4);
        imagen=(ImageView) findViewById(R.id.imageView2);

        textview.setMovementMethod(new ScrollingMovementMethod());
        imagen.setImageResource(fotoId[0]);

    }

}
