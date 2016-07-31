package com.emrekose.copyexternaldbapp;

import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.emrekose.copyexternaldb.CopyDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create instance to CopyDatabaseHelper
        CopyDatabase dbHelper = new CopyDatabase(MainActivity.this,1,"test.db");


        try {
            dbHelper.createDatabase();
        }
        catch (Exception ex) {
            Log.w("DB_ERROR","onCreate(): Could not create and open DB");
        }

        try {
            dbHelper.openDatabase();
        }catch(SQLException e){
            throw e;
        }
    }
}
