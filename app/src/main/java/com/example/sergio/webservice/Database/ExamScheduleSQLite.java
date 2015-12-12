package com.example.sergio.webservice.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.sergio.webservice.Services.Building;
import com.example.sergio.webservice.Services.ExamSchedule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergio on 12/11/15.
 */
public class ExamScheduleSQLite extends BaseDatabase {
    protected String tableName () {return "exam_schedules";}
    public static String sqlCreate () {
        return "create TABLE exam_schedules (" +
                "  id int(10)  not null,\n" +
                "    subject_id int(10)  not null,\n" +
                "    user_id int(10)  not null,\n" +
                "    description varchar(255) not null,\n" +
                "    subject_name varchar(255) not null,\n" +
                "    date_time datetime not null,\n" +
                "    duration decimal(8,2) ,\n" +
                "    created_at timestamp not null default '0000-00-00 00:00:00',\n" +
                "    updated_at timestamp not null default '0000-00-00 00:00:00',\n" +
                "    deleted_at timestamp,\n" +
                "    PRIMARY KEY (id))";
    }

    public ExamScheduleSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    public void insert(ExamSchedule exs){
        ContentValues container = new ContentValues();
        container.put("id",exs.id);
        container.put("subject_id",exs.subjectId);
        container.put("user_id",exs.userId);
        container.put("subject_name", exs.subjectName);
        container.put("description", exs.description);
        container.put("date_time", exs.dateTimeStr);

        getWritableDatabase().insert(tableName(), null, container);
    }

    public List<ExamSchedule> getAll(){
        Cursor c = getWritableDatabase().rawQuery("SELECT *  FROM "+ tableName(), null);
        List<ExamSchedule> thisList = new ArrayList<>();

        if(c.moveToFirst()){
            do{
                thisList.add(
                    new ExamSchedule(
                            c.getInt(c.getColumnIndex("id")),
                            c.getInt(c.getColumnIndex("subject_id")),
                            c.getInt(c.getColumnIndex("user_id")),
                            c.getString(c.getColumnIndex("subject_name")),
                            c.getString(c.getColumnIndex("description")),
                            c.getString(c.getColumnIndex("date_time")))
                );
            }while (c.moveToNext());
        }
        c.close();
        return thisList;

    }
}
