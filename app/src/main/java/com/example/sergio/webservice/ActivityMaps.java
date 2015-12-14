package com.example.sergio.webservice;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sergio.webservice.Services.Building;
import com.example.sergio.webservice.Services.DataReadyListener;
import com.example.sergio.webservice.Services.WebService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ActivityMaps extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;


    private static final String TAG = MainActivity.class.getSimpleName();
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private Location mLastLocation;
    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;
    // boolean flag to toggle periodic location updates
    private boolean mRequestingLocationUpdates = false;
    private LocationRequest mLocationRequest;
    // Location updates intervals in sec
    private static int UPDATE_INTERVAL = 5000; // 10 sec
    private static int FATEST_INTERVAL = 1000; // 5 sec
    private static int DISPLACEMENT = 1; // 10 meters
    // UI elements

    public double lat;
    public double lon;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        WebService.setContext(this);




        this.initMap();
        this.addMartkersFromWebService();
        if (checkPlayServices()) {
            Log.d("CHeck", "play services");
            buildGoogleApiClient();
            createLocationRequest();

        }
    }

    private void initMap(){
        mMap = ((SupportMapFragment)getSupportFragmentManager()
            .findFragmentById(R.id.map)).getMap();

        mMap.getUiSettings().setZoomControlsEnabled(true);

        mMap.setContentDescription("Edificios de la UAA");
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(21.9145483, -102.3149566), 16.5f));

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPlayServices();
        // Resuming the periodic location updates
        if (mGoogleApiClient.isConnected()
                && mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private void  fillMarkers(List<Building> list){
        for (int i = 0; i < list.size(); i++) {
            Building building = list.get(i);
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(building.latitude, building.longitude))
                    .title(building.name)
                    .draggable(false));
        }
    }

    private  void addMartkersFromWebService(){
        Building.getBuildings(new DataReadyListener() {
            @Override
            public void onSuccess(List objects) {
                fillMarkers(objects);
            }

            @Override
            public void onNoNetwork(List objects) {
                fillMarkers(objects);
            }

            @Override
            public void onError(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }
        });
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


    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil
                    .isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil
                        .getErrorDialog(resultCode, this,
                                PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.",
                        Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest
                .PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
        Log.d("REQUEST", "locations request");
    }

    private void togglePeriodicLocationUpdates() {
        if (!mRequestingLocationUpdates) {
            // Changing the button text

            mRequestingLocationUpdates = true;
            // Starting location updates
            startLocationUpdates();
            Log.d(TAG, "Periodic location updates started!");
        } else {
            // Changing the button text

            mRequestingLocationUpdates = false;
            // Stopping location updates
            stopLocationUpdates();
            Log.d(TAG, "Periodic location updates stopped!");
        }
    }

    private void displayLocation() {
        mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            lat = mLastLocation.getLatitude();
            lon = mLastLocation.getLongitude();

            //TODO
            mMap.addMarker( new MarkerOptions()
                    .position(new LatLng(lat, lon))
                    .title("Aquí toy yo!")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                    .draggable(false));
            Toast.makeText(this, "Lat:"+lat+"Lon:"+lon, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "(Couldn't get current location. " +
                    "Make sure location provider is enabled)", Toast.LENGTH_LONG).show();

        }
    }
    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }


    @Override
    public void onConnected(Bundle bundle) {
        Log.e("AAAAAAAAAAAAAAAAAAAAAaa","AAAAAAAAAAAAAAAA");
        displayLocation();
        togglePeriodicLocationUpdates();
        if (mRequestingLocationUpdates) {
            startLocationUpdates();

        }
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }


    @Override
    public void onLocationChanged(Location location) {
        // Assign the new location
        mLastLocation = location;
        Toast.makeText(getApplicationContext(),
                "Location changed!",
                Toast.LENGTH_SHORT).show();
        // Displaying the new location on UI
        displayLocation();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed: " +
                "ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());
    }
}
