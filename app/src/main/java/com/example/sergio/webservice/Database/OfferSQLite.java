package com.example.sergio.webservice.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.sergio.webservice.Services.ExamSchedule;
import com.example.sergio.webservice.Services.Offer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergio on 12/11/15.
 */
public class OfferSQLite extends BaseDatabase {
    protected String tableName () {return "offers";}
    public static String sqlCreate () {
        return "create TABLE offers (" +
                "id int(10)  not null,\n" +
                "    name varchar(255) not null,\n" +
                "    educative_center varchar(255) not null,\n" +
                "    campus varchar(255) not null,\n" +
                "    url varchar(255) not null,\n" +
                "    PRIMARY KEY (id))";
    }

    public OfferSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    public void insert(Offer exs){
        ContentValues container = new ContentValues();
        container.put("id",exs.id);
        container.put("name",exs.name);
        container.put("educative_center",exs.educativeCenter);
        container.put("campus", exs.campus);
        container.put("url", exs.url);

        getWritableDatabase().insert(tableName(), null, container);
    }

    public List<Offer> getAll(){
        Toast.makeText(context, "Loading from cache", Toast.LENGTH_LONG).show();

        Cursor c = getWritableDatabase().rawQuery("SELECT *  FROM "+ tableName(), null);
        List<Offer> thisList = new ArrayList<>();

        if(c.moveToFirst()){
            do{
                thisList.add(
                    new Offer(
                            c.getInt(c.getColumnIndex("id")),
                            c.getString(c.getColumnIndex("name")),
                            c.getString(c.getColumnIndex("educative_center")),
                            c.getString(c.getColumnIndex("campus")),
                            c.getString(c.getColumnIndex("url")))
                );
            }while (c.moveToNext());
        }
        c.close();
        return thisList;

    }
}
