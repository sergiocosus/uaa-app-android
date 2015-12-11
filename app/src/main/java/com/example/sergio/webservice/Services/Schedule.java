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
public class Schedule extends WebService {

    public int id;
    public String weekday;
    public int subjectId;
    public String subjectName;
    public int userId;
    public String time;


    protected final static String DEBUGTAG = "@Shedule";
    public static List<Schedule> lastRequest = null;

    public Schedule(JSONObject jo){
        try{
            id = jo.getInt("id");
            weekday = jo.getString("weekday");
            subjectId  = jo.getInt("subject_id");
            userId  = jo.getInt("user_id");
            subjectName = jo.getString("subject_name");
            time = jo.getString("time");

            Log.i(DEBUGTAG,weekday);
        }catch (JSONException e){
            Log.e(DEBUGTAG,e.getMessage());
        }
    }

    public static void getSchedules(final DataReadyListener dataReadyListener){
        get("schedule", new JsonCustomHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response){
                List<Schedule> thisList = new ArrayList<>();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        thisList.add(new Schedule(response.getJSONObject(i)));
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
