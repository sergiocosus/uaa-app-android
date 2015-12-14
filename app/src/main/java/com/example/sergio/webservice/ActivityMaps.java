package com.example.sergio.webservice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.sergio.webservice.Services.Building;
import com.example.sergio.webservice.Services.DataReadyListener;
import com.example.sergio.webservice.Services.WebService;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ActivityMaps extends AppCompatActivity {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        WebService.setContext(this);
        this.initMap();
        this.addMartker();
    }

    private  void addMartker(){
        Building.getBuildings(new DataReadyListener() {
            @Override
            public void onSuccess(List objects) {
                List<Building> list = objects;
                for (int i = 0; i < objects.size(); i++) {
                    Building building = list.get(i);
                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(building.latitude, building.longitude))
                            .title(building.name)
                            .draggable(false));
                }
            }

            @Override
            public void onNoNetwork(List objects) {

            }

            @Override
            public void onError(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }
        });



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
