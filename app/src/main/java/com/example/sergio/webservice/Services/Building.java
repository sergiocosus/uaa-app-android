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
            log();
        }catch (JSONException e){
            Log.e(DEBUGTAG,e.getMessage());
        }
    }

    public void log(){
        Log.i(DEBUGTAG, id+":"+name+":"+latitude+":"+longitude);

    }

    public static void getBuildings(final DataReadyListener dataReadyListener){
        get("building", new JsonCustomHandler(){
            @Override
            public void globalSuccess(int statusCode, Header[] headers, JSONArray jsonArray, JSONObject jsonObject, String responseString) {
                List<Building> thisList = new ArrayList<>();
                try {
                    BuildingSQLite sql = new BuildingSQLite(context, database,null,1);
                    sql.deleteAll();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Building building = new Building(jsonArray.getJSONObject(i));
                        thisList.add(building);
                        sql.insert(building);
                    }

                    lastRequest = thisList;
                    dataReadyListener.onSuccess(thisList);
                } catch (JSONException e) {
                    Log.e(DEBUGTAG, e.getMessage());
                    dataReadyListener.onSuccess(null);
                }
            }

            @Override
            public void globalError(int statusCode, Header[] headers, JSONArray jsonArray, JSONObject jsonObject, String responseString) {
                dataReadyListener.onError(statusCode, headers, responseString,null);
            }

        });
    }
}
