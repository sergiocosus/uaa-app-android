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
public class ExamSchedule extends WebService {

    public int id;
    public int subjectId;
    public int userId;
    public String subjectName;
    public String description;
    public Date dateTime;

    protected final static String DEBUGTAG = "@BuildingModel";
    public static List<ExamSchedule> lastRequest = null;

    public ExamSchedule(JSONObject jo){
        try{
            id = jo.getInt("id");
            subjectId  = jo.getInt("subject_id");
            userId  = jo.getInt("user_id");
            description  = jo.getString("description");
            dateTime = toDate(jo.getString("date_time"));
            subjectName = jo.getString("subject_name");

            Log.i(DEBUGTAG,dateTime.toString());
        }catch (JSONException e){
            Log.e(DEBUGTAG,e.getMessage());
        }
    }

    public static void getExamSchedules(final DataReadyListener dataReadyListener){
        get("exam-schedule", new JsonCustomHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response){
                List<ExamSchedule> thisList = new ArrayList<>();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        thisList.add(new ExamSchedule(response.getJSONObject(i)));
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
