package com.example.sergio.webservice;

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


    public Building(JSONObject jo){
        try{
            id = jo.getInt("id");
            name  = jo.getString("name");
            latitude = jo.getDouble("latitude");
            longitude = jo.getDouble("longitude");
            Log.i("asdf",name);
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
                        JSONObject jsonObjCar = response.getJSONObject(i);
                        buildingList.add(new Building(jsonObjCar));
                    }
                    final StringBuilder output = new StringBuilder("");
                    for (Building bu : buildingList) {
                        output.append("id: ")
                                .append(bu.id)
                                .append(" - ")
                                .append(bu.name)
                                .append('\n');
                    }
                    dataReadyListener.onSuccess(buildingList);

                } catch (JSONException e) {
                    Log.e(DEBUGTAG, e.getMessage());
                }
            }



            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                dataReadyListener.onError(statusCode, headers, responseString,throwable);
            }
        });
    }
}
