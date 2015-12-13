package com.example.sergio.webservice;

import android.os.Bundle;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;

import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sergio.webservice.Services.DataReadyListener;
import com.example.sergio.webservice.Services.Offer;
import com.example.sergio.webservice.Services.WebService;

import cz.msebera.android.httpclient.Header;


public class PlanesEstudioE extends Activity {

    private Spinner spinNorte, spinSur;
    private Button btnSubmit;
    private List<String> ArrayOffer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planes_estudio_e);
        spinSur=(Spinner)findViewById(R.id.spin_sur);

       // addItemsOnSpinner2();
        addListenerOnButton();
        addListenerOnSpinnerItemSelection();

        WebService.setContext(this);
    }

    // add items into spinner dynamically
  /*  public void addItemsOnSpinner2() {

        spinSur = (Spinner) findViewById(R.id.spin_sur);
        List<String> list = new ArrayList<String>();
        list.add("list 1");
        list.add("list 2");
        list.add("list 3");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinSur.setAdapter(dataAdapter);
    }*/

    public void addListenerOnSpinnerItemSelection() {

    }

    // get the selected dropdown list value
    public void addListenerOnButton() {

        spinNorte = (Spinner) findViewById(R.id.spin_norte);
        spinSur = (Spinner) findViewById(R.id.spin_sur);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        String [] ArregloOpciones= {"Campus Norte","Campus Sur"};
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,ArregloOpciones);
        spinNorte.setAdapter(adaptador);

        btnSubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Offer.getOffers(new DataReadyListener() {
                    @Override
                    public void onSuccess(List objects) {

                      ArrayOffer=new ArrayList<String>();
                        for(int i=0;i<=30;i++) {
                           Offer offer = (Offer) objects.get(i);
                            ArrayOffer.add(offer.name);

                        }
                        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(PlanesEstudioE.this,android.R.layout.simple_spinner_item,ArrayOffer);
                        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinSur.setAdapter(adaptador);

                    //    Toast.makeText(PlanesEstudioE.this,offer.educativeCenter,Toast.LENGTH_LONG).show();
                    //    btnSubmit.setText(offer.name);
                    }

                    @Override
                    public void onError(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                    }
                });
                Toast.makeText(PlanesEstudioE.this,
                        "Planes : " +
                                "\nCampus Central : "+ String.valueOf(spinNorte.getSelectedItem()) +
                                "\nCampus Sur : "+ String.valueOf(spinSur.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();
            }

        });}



    public void RellenarCampus() {
        Offer.getOffers(new DataReadyListener() {
            @Override
            public void onSuccess(List objects) {

                String seleccion = spinSur.getSelectedItem().toString();

                if (seleccion.equals("Campus Norte")) {


                }

            }

            @Override
            public void onError(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }


        });
    }



}



