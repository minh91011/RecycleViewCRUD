package com.example.recycleviewcrud;

import static com.example.recycleviewcrud.DBMain.TABLENAME;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
DBMain dbmain;
SQLiteDatabase sqLiteDatabase;
EditText fname,lname;
Button submit, edit, display;
int id =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbmain = new DBMain(this);
        findid();
        insertData();
        editData();
    }

    private void editData() {
        if(getIntent().getBundleExtra("userdata")!=null){
            Bundle bundle=getIntent().getBundleExtra("userdata");
            id=bundle.getInt("id");
            fname.setText(bundle.getString("fname"));
            lname.setText(bundle.getString("lname"));
            edit.setVisibility(View.VISIBLE);
            submit.setVisibility(View.GONE);
        }
    }

    private void insertData() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues cv = new ContentValues();
                cv.put("fname",fname.getText().toString());
                cv.put("lname",lname.getText().toString());

                sqLiteDatabase=dbmain.getWritableDatabase();
                Long recinsert = sqLiteDatabase.insert(TABLENAME,null,cv);
                if(recinsert!=null){
                    Toast.makeText(MainActivity.this, "success inserted", Toast.LENGTH_SHORT).show();
                    //clear data
                    fname.setText("");
                    lname.setText("");
                }else{
                    Toast.makeText(MainActivity.this, "fail!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //when click on display button open display data activity
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DisplayData.class);
                startActivity(intent);
            }
        });
        //storing edited data
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cv = new ContentValues();
                cv.put("fname",fname.getText().toString());
                cv.put("lname",fname.getText().toString());

                sqLiteDatabase=dbmain.getReadableDatabase();
                long recedit = sqLiteDatabase.update(TABLENAME,cv,"id="+id,null);
                if(recedit!=-1){
                    Toast.makeText(MainActivity.this, "Data updated", Toast.LENGTH_SHORT).show();
//                    submit.setVisibility(View.VISIBLE);
                    edit.setVisibility(View.GONE);
                }else{
                    Toast.makeText(MainActivity.this, "Update failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void findid() {
        fname = (EditText) findViewById(R.id.edit_fname);
        lname = (EditText) findViewById(R.id.edit_lname);
        submit = (Button) findViewById(R.id.btnsubmit);
        display = (Button) findViewById(R.id.btndisplay);
        edit = (Button) findViewById(R.id.btnedit);
    }
}