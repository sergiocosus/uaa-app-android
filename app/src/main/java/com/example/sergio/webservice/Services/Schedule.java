package com.example.sergio.webservice.Services;

import android.util.Log;

import com.example.sergio.webservice.Database.AcademicCalendarSQLite;
import com.example.sergio.webservice.Database.BuildingSQLite;
import com.example.sergio.webservice.Database.ScheduleSQLite;

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

    public Schedule(int id, String weekday, int subjectId, String subjectName, int userId, String time) {
        this.id = id;
        this.weekday = weekday;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.userId = userId;
        this.time = time;
    }

    public Schedule(JSONObject jo){
        try{
            id = jo.getInt("id");
            weekday = jo.getString("weekday");
            subjectId  = jo.getInt("subject_id");
            userId  = jo.getInt("user_id");
            subjectName = jo.getString("subject_name");
            time = jo.getString("time");
            log();
        }catch (JSONException e){
            Log.e(DEBUGTAG,e.getMessage());
        }
    }

    public void log(){
        Log.i(DEBUGTAG, id+":"+weekday+":"+subjectId+":"+userId+":"+subjectName+":"+time);
    }

    public static void getSchedules(final DataReadyListener dataReadyListener){
        if(isConnected()){
            get("schedule", new JsonCustomHandler(){
                @Override
                public void globalSuccess(int statusCode, Header[] headers, JSONArray jsonArray, JSONObject jsonObject, String responseString) {
                    List<Schedule> thisList = new ArrayList<>();
                    try {
                        ScheduleSQLite sql = new ScheduleSQLite(context);
                        sql.deleteAll();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Schedule schedule = new Schedule(jsonArray.getJSONObject(i));
                            thisList.add(schedule);
                            sql.insert(schedule);
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
            ScheduleSQLite scheduleSQLite = new ScheduleSQLite(context);
            List <Schedule> schedules = scheduleSQLite.getAll();
            dataReadyListener.onNoNetwork(schedules);
        }
    }
}
