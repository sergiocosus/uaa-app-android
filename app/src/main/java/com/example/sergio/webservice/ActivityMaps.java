package com.example.sergio.webservice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ActivityMaps extends AppCompatActivity {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        this.initMap();
        this.addMartker();
    }

    private  void addMartker(){
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(21.9135183, -102.3157933))
                .title("Edificio 55")
                .snippet("Estamos cerca de aquí")
                .draggable(false));
    }

    private void initMap(){
        mMap = ((SupportMapFragment)getSupportFragmentManager()
            .findFragmentById(R.id.map)).getMap();

        mMap.getUiSettings().setZoomControlsEnabled(true);

        mMap.setContentDescription("Edificios de la UAA");
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
