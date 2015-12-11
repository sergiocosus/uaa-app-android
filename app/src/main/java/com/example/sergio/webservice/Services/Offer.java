package com.example.sergio.webservice.Services;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by sergio on 12/7/15.
 */
public class Offer extends WebService {

    public int id;
    public String name;
    public String educativeCenter;
    public String campus;
    public String url;

    protected final static String DEBUGTAG = "@Offer";
    public static List<Offer> lastRequest = null;

    public Offer(JSONObject jo){
        try{
            id = jo.getInt("id");
            educativeCenter = jo.getString("educative_center");
            name = jo.getString("name");
            campus = jo.getString("campus");
            url = jo.getString("url");

            Log.i(DEBUGTAG,name);
        }catch (JSONException e){
            Log.e(DEBUGTAG,e.getMessage());
        }
    }

    public static void getOffers(final DataReadyListener dataReadyListener){
        get("offer", new JsonCustomHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response){
                List<Offer> thisList = new ArrayList<>();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        thisList.add(new Offer(response.getJSONObject(i)));
                    }
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
