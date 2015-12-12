package com.example.sergio.webservice.Services;

import android.util.Log;

import com.example.sergio.webservice.Database.AcademicCalendarSQLite;
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
            log();
        }catch (JSONException e){
            Log.e(DEBUGTAG,e.getMessage());
        }
    }

    public void log(){
        Log.i(DEBUGTAG, id+":"+name+":"+educativeCenter+":"+campus+":"+url);

    }

    public static void getOffers(final DataReadyListener dataReadyListener){
        if(isConnected()){
            get("offer", new JsonCustomHandler(){
                @Override
                public void globalSuccess(int statusCode, Header[] headers, JSONArray jsonArray, JSONObject jsonObject, String responseString) {
                    List<Offer> thisList = new ArrayList<>();
                    try {
                        OfferSQLite sql = new OfferSQLite(context);
                        sql.deleteAll();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Offer offer = new Offer(jsonArray.getJSONObject(i));
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
                public void globalError(int statusCode, Header[] headers, JSONArray jsonArray, JSONObject jsonObject, String responseString) {
                    dataReadyListener.onError(statusCode, headers, responseString,null);
                }
            });
        }else{
            OfferSQLite offerSQLite = new OfferSQLite(context);
            List <Offer> offers = offerSQLite.getAll();
            dataReadyListener.onNoNetwork(offers);
        }
    }
}
