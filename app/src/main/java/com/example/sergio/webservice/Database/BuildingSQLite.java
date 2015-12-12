package com.example.sergio.webservice.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.sergio.webservice.Services.AcademicCalendar;
import com.example.sergio.webservice.Services.Building;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergio on 12/11/15.
 */
public class BuildingSQLite extends BaseDatabase {
    protected String tableName () {return "buildings";}
    public static String sqlCreate () {
        return "create TABLE buildings (" +
                " id int(10)  not null,\n" +
                "    name varchar(255) not null,\n" +
                "    latitude double not null,\n" +
                "    longitude double not null,\n" +
                "    created_at timestamp not null default '0000-00-00 00:00:00',\n" +
                "    updated_at timestamp not null default '0000-00-00 00:00:00',\n" +
                "    deleted_at timestamp,\n" +
                "    PRIMARY KEY (id))";
    }

    public BuildingSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    public void insert(Building calendar){
        Log.i("DATABASE","insert  In database");
        ContentValues container = new ContentValues();
        container.put("id",calendar.id);
        container.put("name",calendar.name);
        container.put("longitude",calendar.longitude);
        container.put("latitude", calendar.latitude);
        getWritableDatabase().insert(tableName(), null, container);
    }

    public List<Building> getAll(){
        Cursor c = getWritableDatabase().rawQuery("SELECT *  FROM "+ tableName(), null);
        List<Building> thisList = new ArrayList<>();

        if(c.moveToFirst()){
            do{
                thisList.add(
                    new Building(
                            c.getInt(c.getColumnIndex("id")),
                            c.getString(c.getColumnIndex("name")),
                            c.getDouble(c.getColumnIndex("latitude")),
                            c.getDouble(c.getColumnIndex("longitude")))
                );
            }while (c.moveToNext());
        }
        c.close();
        return thisList;

    }
}
