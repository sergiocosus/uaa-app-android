package com.example.sergio.webservice.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sergio.webservice.Services.ExamSchedule;
import com.example.sergio.webservice.Services.WebService;

/**
 * Created by sergio on 12/11/15.
 */
public  abstract class BaseDatabase extends SQLiteOpenHelper {
    protected abstract String tableName ();

    protected  Context context;
    public BaseDatabase(Context context) {
        super(context, WebService.database, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AcademicCalendarSQLite.sqlCreate());
        db.execSQL(BuildingSQLite.sqlCreate());
        db.execSQL(ExamScheduleSQLite.sqlCreate());
        db.execSQL(OfferSQLite.sqlCreate());
        db.execSQL(ScheduleSQLite.sqlCreate());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS buildings");
            db.execSQL("DROP TABLE IF EXISTS exam_schedules" );
            db.execSQL("DROP TABLE IF EXISTS offers" );
            db.execSQL("DROP TABLE IF EXISTS schedules" );
            db.execSQL("DROP TABLE IF EXISTS academic_calendars" );

            db.execSQL(AcademicCalendarSQLite.sqlCreate());
            db.execSQL(BuildingSQLite.sqlCreate());
            db.execSQL(ExamScheduleSQLite.sqlCreate());
            db.execSQL(OfferSQLite.sqlCreate());
            db.execSQL(ScheduleSQLite.sqlCreate());
        }
    }

    public void deleteAll(){
        String sql = "DELETE FROM "+tableName();
        getWritableDatabase().execSQL(sql);
    }
}