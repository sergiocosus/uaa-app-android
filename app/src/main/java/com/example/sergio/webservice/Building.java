package com.example.sergio.webservice;

import android.os.Build;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by sergio on 12/7/15.
 */
public class Building extends WebService {
    protected final static String SERVICE =  "building/";

    public int id;
    public String name;
    public double latitude;
    public double longitude;

    public List<Building> lastRequest;

    public Building(JSONObject jo){
        try{
            id = jo.getInt("id");
            name  = jo.getString("name");
            latitude = jo.getDouble("latitude");
            longitude = jo.getDouble("longitude");
        }catch (JSONException e){
            Log.e(DEBUGTAG,e.getMessage());
        }
    }

    public static void getBuildings(final DataReadyListener dataReadyListener){
        get("building", new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response){
                List<Building> buildingList = new ArrayList<>();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        buildingList.add(new Building(response.getJSONObject(i)));
                    }

                    dataReadyListener.onSuccess(buildingList);

                } catch (JSONException e) {
                    Log.e(DEBUGTAG, e.getMessage());
                    dataReadyListener.onSuccess(null);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                dataReadyListener.onError(statusCode, headers, responseString,throwable);
            }
        });
    }
}
