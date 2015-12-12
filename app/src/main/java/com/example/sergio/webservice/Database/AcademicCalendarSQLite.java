package com.example.sergio.webservice.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.sergio.webservice.Services.AcademicCalendar;

import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergio on 12/11/15.
 */
public class AcademicCalendarSQLite extends BaseDatabase {
    protected String tableName () {return "academic_calendars";}
    public static String sqlCreate () {
        return "create TABLE academic_calendars (" +
                "id integer(10)  not null," +
                "begin_at timestamp not null default '0000-00-00 00:00:00'," +
                "end_at timestamp not null default '0000-00-00 00:00:00'," +
                "name varchar(255) not null," +
                "created_at timestamp not null default '0000-00-00 00:00:00'," +
                "updated_at timestamp not null default '0000-00-00 00:00:00'," +
                "PRIMARY KEY (id))";
    }

    public AcademicCalendarSQLite(Context context) {
        super(context);
    }


    public void insert(AcademicCalendar calendar){
        ContentValues container = new ContentValues();
        container.put("id",calendar.id);
        container.put("name",calendar.name);
        container.put("begin_at",calendar.beginAtStr);
        container.put("end_at", calendar.endAtStr);
        getWritableDatabase().insert(tableName(), null, container);
    }

    public List<AcademicCalendar> getAll(){
        Toast.makeText(context,"Loading from cache",Toast.LENGTH_LONG).show();
        Cursor c = getWritableDatabase().rawQuery("SELECT *  FROM "+ tableName(), null);
        List<AcademicCalendar> thisList = new ArrayList<>();

        if(c.moveToFirst()){
            do{
                thisList.add(
                    new AcademicCalendar(
                            c.getInt(c.getColumnIndex("id")),
                            c.getString(c.getColumnIndex("name")),
                            c.getString(c.getColumnIndex("begin_at")),
                            c.getString(c.getColumnIndex("end_at")))
                );
            }while (c.moveToNext());
        }
        c.close();
        return thisList;

    }
}
