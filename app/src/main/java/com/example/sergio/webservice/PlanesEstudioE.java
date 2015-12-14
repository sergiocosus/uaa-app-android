package com.example.sergio.webservice;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;

import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sergio.webservice.Services.DataReadyListener;
import com.example.sergio.webservice.Services.Offer;
import com.example.sergio.webservice.Services.WebService;

import cz.msebera.android.httpclient.Header;


public class PlanesEstudioE extends Activity  {

    private WebView myWebView;
    private Spinner spinNorte, spinSur;
    private Button btnSubmit;
    private List<String> ArrayOffer,ArrayOffer2;
    String pdf="http://www.uaa.mx/direcciones/dgdp/catalogo/ciencias_empresariales/logistica_empresarial.pdf";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planes_estudio_e);
        spinSur=(Spinner)findViewById(R.id.spin_sur);
        spinNorte=(Spinner)findViewById(R.id.spin_norte);
        myWebView=(WebView)findViewById(R.id.webView);

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



        spinNorte.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Offer.getOffers(new DataReadyListener() {
                    @Override
                    public void onSuccess(List objects) {
                        Log.i("MAo", "Entró a rellenarCampus");
                        String seleccion = spinNorte.getSelectedItem().toString();
                        String seleccion2 = spinSur.getSelectedItem().toString();
                        Log.i("MAo", "Entró a rellenarCampus" + seleccion);
                        Log.i("MAo", "Entró a rellenarCampus222" + seleccion2);
                        if (seleccion.equals("Campus Norte")) {

                            Log.i("MAo", "Entró a rellenarCampus Central");
                            ArrayOffer = new ArrayList<String>();
                            for (int i = 0; i<objects.size(); i++) {
                                Offer offer = (Offer) objects.get(i);
                                if (offer.campus.equals("Central")) {
                                    ArrayOffer.add(offer.name);
                                }

                            }
                            ArrayAdapter<String> adaptador = new ArrayAdapter<String>(PlanesEstudioE.this, android.R.layout.simple_spinner_item, ArrayOffer);
                            adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinSur.setAdapter(adaptador);

                        }

                        if (seleccion.equals("Campus Sur")) {

                            Log.i("MAo", "Entró a rellenarCampus Sur");
                            ArrayOffer2 = new ArrayList<String>();
                            for (int i = 0; i <objects.size(); i++) {
                                Offer offer = (Offer) objects.get(i);
                                if (offer.campus.equals("Sur")) {
                                    ArrayOffer2.add(offer.name);
                                }

                            }
                            ArrayAdapter<String> adaptador = new ArrayAdapter<String>(PlanesEstudioE.this, android.R.layout.simple_spinner_item, ArrayOffer2);
                            adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinSur.setAdapter(adaptador);

                        }



                    }

                    @Override
                    public void onError(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                    }

                    @Override
                    public void onNoNetwork(List objects) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        btnSubmit.setOnClickListener(new OnClickListener() {





            @Override
            public void onClick(View v) {
                Offer.getOffers(new DataReadyListener() {
                    @Override
                    public void onSuccess(List objects) {

                        String url="";
                        String seleccion2 = spinSur.getSelectedItem().toString();
                        Log.i("MAo", "Entró a rellenarCampuser2" + seleccion2);
                        ArrayOffer = new ArrayList<String>();
                        for (int i = 0; i <objects.size(); i++) {
                            Offer offer = (Offer) objects.get(i);
                            if(seleccion2.equals(offer.name)) {
                               url=offer.url;
                                Log.i("MAo", "Entró a rellenarCampuser2URL" + url);
                            }
                        }






                        myWebView=(WebView)findViewById(R.id.webView);
                        myWebView.getSettings().setJavaScriptEnabled(true);
                        myWebView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url="+url);





                        //    Toast.makeText(PlanesEstudioE.this,offer.educativeCenter,Toast.LENGTH_LONG).show();
                        //    btnSubmit.setText(offer.name);
                    }

                    @Override
                    public void onError(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                    }

                    @Override
                    public void onNoNetwork(List objects) {

                    }
                });
                Toast.makeText(PlanesEstudioE.this,
                        "Planes : " +
                                "\nCampus Central : " + String.valueOf(spinNorte.getSelectedItem()) +
                                "\nCampus Sur : " + String.valueOf(spinSur.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();
            }

        });}






}



