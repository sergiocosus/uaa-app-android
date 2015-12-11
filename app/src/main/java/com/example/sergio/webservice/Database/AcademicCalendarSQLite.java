package com.example.sergio.webservice.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.sergio.webservice.Services.AcademicCalendar;

import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergio on 12/11/15.
 */
public class AcademicCalendarSQLite extends SQLiteOpenHelper {
    private String tableName = "academic_calendars";
    private String sqlCreate = "create TABLE "+tableName+" ("+
            "id integer(10)  not null,"+
            "begin_at timestamp not null default '0000-00-00 00:00:00',"+
            "end_at timestamp not null default '0000-00-00 00:00:00',"+
            "name varchar(255) not null,"+
            "created_at timestamp not null default '0000-00-00 00:00:00',"+
            "updated_at timestamp not null default '0000-00-00 00:00:00',"+
            "PRIMARY KEY (id))";


    public AcademicCalendarSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS academic_calendars");
        db.execSQL(sqlCreate);
    }

    public void insert(AcademicCalendar calendar){
        Log.i("DATABASE","insert  In database");
        ContentValues container = new ContentValues();
        container.put("id",calendar.id);
        container.put("name",calendar.name);
        container.put("begin_at",calendar.beginAtStr);
        container.put("end_at", calendar.endAtStr);
        getWritableDatabase().insert(tableName, null, container);
    }

    public void deleteAll(){
        String sql = "DELETE FROM "+tableName;
        getWritableDatabase().execSQL(sql);
    }

    public List<AcademicCalendar> getAll(){
        Cursor c = getWritableDatabase().rawQuery("SELECT *  FROM "+ tableName, null);
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
