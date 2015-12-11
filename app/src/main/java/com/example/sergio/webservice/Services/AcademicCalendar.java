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
public class AcademicCalendar extends WebService {

    public int id;
    public Date  beginAt;
    public Date endAt;
    public String name;


    protected final static String DEBUGTAG = "@Shedule";
    public static List<AcademicCalendar> lastRequest = null;

    public AcademicCalendar(JSONObject jo){
        try{
            id = jo.getInt("id");
            beginAt = toDate(jo.getString("begin_at"));
            endAt  = toDate(jo.getString("end_at"));
            name  = jo.getString("name");

            Log.i(DEBUGTAG,name);
        }catch (JSONException e){
            Log.e(DEBUGTAG,e.getMessage());
        }
    }

    public static void getAcademicCalendar(final DataReadyListener dataReadyListener){
        get("academic-calendar", new JsonCustomHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response){
                List<AcademicCalendar> thisList = new ArrayList<>();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        thisList.add(new AcademicCalendar(response.getJSONObject(i)));
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
