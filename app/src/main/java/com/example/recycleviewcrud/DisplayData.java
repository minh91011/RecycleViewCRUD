package com.example.recycleviewcrud;

import static com.example.recycleviewcrud.DBMain.TABLENAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.ArrayList;

public class DisplayData extends AppCompatActivity {
    DBMain dbMain;
    SQLiteDatabase sqLiteDatabase;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);
        dbMain = new DBMain(this);
        findid();
        displayData();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void displayData() {
        sqLiteDatabase=dbMain.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+TABLENAME+"",null);
        ArrayList<Model> list = new ArrayList<>();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String fname = cursor.getString(1);
            String lname = cursor.getString(2);
            list.add(new Model(id,fname,lname));
        }
        cursor.close();
        myAdapter = new MyAdapter(this,R.layout.singledata,list,sqLiteDatabase);
        recyclerView.setAdapter(myAdapter);
    }

    private void findid() {
        recyclerView = findViewById(R.id.rv);
    }
}