package com.example.sergio.webservice.Services;

import android.util.Log;

import com.example.sergio.webservice.Database.BuildingSQLite;
import com.example.sergio.webservice.Database.OfferSQLite;

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

    public Offer(int id, String name, String educativeCenter, String campus, String url) {
        this.id = id;
        this.name = name;
        this.educativeCenter = educativeCenter;
        this.campus = campus;
        this.url = url;
    }

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
                    OfferSQLite sql = new OfferSQLite(context, database,null,1);
                    sql.deleteAll();
                    for (int i = 0; i < response.length(); i++) {
                        Offer offer = new Offer(response.getJSONObject(i));
                        thisList.add(offer);
                        sql.insert(offer);
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
