package com.example.sergio.webservice.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.sergio.webservice.Services.Offer;
import com.example.sergio.webservice.Services.Schedule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergio on 12/11/15.
 */
public class ScheduleSQLite extends BaseDatabase {
    protected String tableName () {return "schedules";}
    public static String sqlCreate () {
        return "create TABLE schedules (" +
                "  id integer(10)  not null,\n" +
                "    weekday varchar(255) not null,\n" +
                "    subject_id integer(10) not null,\n" +
                "    user_id integer(10) not null,\n" +
                "    \"time\" time not null,\n" +
                "    subject_name varchar(255) not null,\n" +

                "    created_at timestamp not null default '0000-00-00 00:00:00',\n" +
                "    updated_at timestamp not null default '0000-00-00 00:00:00',\n" +
                "    deleted_at timestamp,\n" +
                "    PRIMARY KEY (id))";
    }

    public ScheduleSQLite(Context context) {
        super(context);
    }


    public void insert(Schedule exs){
        ContentValues container = new ContentValues();
        container.put("id",exs.id);
        container.put("weekday",exs.weekday);
        container.put("subject_id",exs.subjectId);
        container.put("user_id",exs.userId);
        container.put("subject_name", exs.subjectName);
        container.put("time", exs.time);

        getWritableDatabase().insert(tableName(), null, container);
    }

    public List<Schedule> getAll(){
        Toast.makeText(context, "Loading from cache", Toast.LENGTH_LONG).show();

        Cursor c = getWritableDatabase().rawQuery("SELECT *  FROM "+ tableName(), null);
        List<Schedule> thisList = new ArrayList<>();

        if(c.moveToFirst()){
            do{
                thisList.add(
                    new Schedule(
                            c.getInt(c.getColumnIndex("id")),
                            c.getString(c.getColumnIndex("weekday")),
                            c.getInt(c.getColumnIndex("subject_id")),
                            c.getString(c.getColumnIndex("subject_name")),
                            c.getInt(c.getColumnIndex("user_id")),
                            c.getString(c.getColumnIndex("time")))
                );
            }while (c.moveToNext());
        }
        c.close();
        return thisList;

    }
}
