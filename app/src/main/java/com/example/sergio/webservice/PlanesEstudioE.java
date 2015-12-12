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



public class PlanesEstudioE extends Activity {

    private Spinner spinNorte, spinSur;
    private Button btnSubmit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planes_estudio_e);

       // addItemsOnSpinner2();
        addListenerOnButton();
        addListenerOnSpinnerItemSelection();
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
        spinNorte = (Spinner) findViewById(R.id.spin_norte);
        spinNorte.setOnItemSelectedListener(new onPlanSelected());
        spinSur = (Spinner) findViewById(R.id.spin_norte);
        spinSur.setOnItemSelectedListener(new onPlanSelected());
    }

    // get the selected dropdown list value
    public void addListenerOnButton() {

        spinNorte = (Spinner) findViewById(R.id.spin_norte);
        spinSur = (Spinner) findViewById(R.id.spin_sur);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(PlanesEstudioE.this,
                        "Planes : " +
                                "\nCampus Central : "+ String.valueOf(spinNorte.getSelectedItem()) +
                                "\nCampus Sur : "+ String.valueOf(spinSur.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();
            }

        });
    }






}
