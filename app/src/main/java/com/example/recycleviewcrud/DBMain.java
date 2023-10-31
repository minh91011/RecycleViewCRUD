package com.example.recycleviewcrud;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBMain extends SQLiteOpenHelper {
    public  static final String DBNAME = "student.db";
    public  static final String TABLENAME = "course";
    public  static  final int VER = 1;
    String query;
    public DBMain(@Nullable Context context) {
        super(context, DBNAME, null, VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        query = "create table "+TABLENAME+"(id integer primary key, fname text, lname text)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        query = "drop table if exists "+TABLENAME+"";
        db.execSQL(query);
        onCreate(db);
    }
}
