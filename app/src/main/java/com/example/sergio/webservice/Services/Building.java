package com.example.sergio.webservice.Services;

import android.util.Log;

import com.example.sergio.webservice.Database.AcademicCalendarSQLite;
import com.example.sergio.webservice.Database.BuildingSQLite;
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

    public int id;
    public String name;
    public double latitude;
    public double longitude;
    protected final static String DEBUGTAG = "@BuildingModel";
    public static List<Building> lastRequest = null;

    public Building(int id, String name, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Building(JSONObject jo){
        try{
            id = jo.getInt("id");
            name  = jo.getString("name");
            latitude = jo.getDouble("latitude");
            longitude = jo.getDouble("longitude");

            Log.i(DEBUGTAG,name);
        }catch (JSONException e){
            Log.e(DEBUGTAG,e.getMessage());
        }
    }

    public static void getBuildings(final DataReadyListener dataReadyListener){
        get("building", new JsonCustomHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response){
                List<Building> thisList = new ArrayList<>();
                try {
                    BuildingSQLite sql = new BuildingSQLite(context, database,null,1);
                    sql.deleteAll();
                    for (int i = 0; i < response.length(); i++) {
                        Building building = new Building(response.getJSONObject(i));
                        thisList.add(building);
                        sql.insert(building);
                    }

                    Log.d("DATABASE",(sql.getAll().get(0).name));
                    lastRequest = thisList;
                    dataReadyListener.onSuccess(thisList);
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
