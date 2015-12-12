package com.example.sergio.webservice.Services;

import android.util.Log;

import com.example.sergio.webservice.Database.AcademicCalendarSQLite;

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

    public String beginAtStr;
    public String endAtStr;

    protected final static String DEBUGTAG = "@AcademicCalendar";
    public static List<AcademicCalendar> lastRequest = null;

    public AcademicCalendar(int id, String name, String beginAtStr, String endAtStr) {
        this.id = id;
        this.name = name;
        this.beginAtStr = beginAtStr;
        this.endAtStr = endAtStr;
        this.beginAt = toDate(beginAtStr);
        this.endAt  = toDate(endAtStr);
        Log.i(DEBUGTAG, id+":"+name+":"+beginAtStr+":"+endAtStr);
    }

    public AcademicCalendar(JSONObject jo) throws JSONException {
        this( jo.getInt("id"),jo.getString("name"),jo.getString("begin_at"),jo.getString("end_at"));
    }

    public static void getAcademicCalendar(final DataReadyListener dataReadyListener){
        get("academic-calendar", new JsonCustomHandler(){
            @Override
            public void globalSuccess(int statusCode, Header[] headers, JSONArray jsonArray, JSONObject jsonObject, String responseString) {
                List<AcademicCalendar> thisList = new ArrayList<>();
                try {
                    AcademicCalendarSQLite sql = new AcademicCalendarSQLite(context, database,null,1);
                    sql.deleteAll();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        AcademicCalendar academicCalendar = new AcademicCalendar(jsonArray.getJSONObject(i));
                        thisList.add(academicCalendar);
                        sql.insert(academicCalendar);
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
