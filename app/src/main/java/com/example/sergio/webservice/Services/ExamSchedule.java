package com.example.sergio.webservice.Services;

import android.util.Log;

import com.example.sergio.webservice.Database.BuildingSQLite;
import com.example.sergio.webservice.Database.ExamScheduleSQLite;

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

    public String dateTimeStr;

    public float duration;

    protected final static String DEBUGTAG = "@ExamSchedule";
    public static List<ExamSchedule> lastRequest = null;

    public ExamSchedule(int id, int subjectId, int userId, String subjectName, String description, String dateTimeStr) {
        this.id = id;
        this.subjectId = subjectId;
        this.userId = userId;
        this.subjectName = subjectName;
        this.description = description;
        this.dateTimeStr = dateTimeStr;

        this.dateTime = toDate(this.dateTimeStr);
    }

    public ExamSchedule(JSONObject jo){
        try{
            id = jo.getInt("id");
            subjectId  = jo.getInt("subject_id");
            userId  = jo.getInt("user_id");
            description  = jo.getString("description");

            dateTimeStr = jo.getString("date_time");
            dateTime = toDate(dateTimeStr);
            subjectName = jo.getString("subject_name");

        }catch (JSONException e){
            Log.e(DEBUGTAG,e.getMessage());
        }
    }

    public void log(){
        Log.i(DEBUGTAG, id+":"+subjectId+":"+subjectName+":"+description+":"+dateTimeStr);
    }

    public static void getExamSchedules(final DataReadyListener dataReadyListener){
        get("exam-schedule", new JsonCustomHandler(){
            @Override
            public void globalSuccess(int statusCode, Header[] headers, JSONArray jsonArray, JSONObject jsonObject, String responseString) {
                List<ExamSchedule> thisList = new ArrayList<>();
                try {
                    ExamScheduleSQLite sql = new ExamScheduleSQLite(context, database,null,1);
                    sql.deleteAll();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        ExamSchedule examSchedule = new ExamSchedule(jsonArray.getJSONObject(i));
                        thisList.add(examSchedule);
                        sql.insert(examSchedule);
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
