package com.emrekose.copyexternaldbapp;

import android.database.Cursor;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView mListView;
    ArrayList<String> mArrayList;
    ArrayAdapter<String> mArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initial
        mListView = (ListView)findViewById(R.id.listView);
        mArrayList = new ArrayList<>();
        mArrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                mArrayList);

        // copy database in assets folder and create instance of own DatabaseHelper
        DatabaseHelper databaseHelper = new DatabaseHelper(this,1,"test.db");

        // extarnal database copy process
        try{

            databaseHelper.createDatabase();

        }catch (Exception e){

            e.printStackTrace();

            Log.e("DB_ERROR","onCreate(): Could not create and open DB");

        }

        // extarnal database open process
        try{

            databaseHelper.openDatabase();

        }catch (SQLException e){

            e.printStackTrace();

        }

        // add listview of database values
        Cursor cursor = databaseHelper.getAllRows();

        while (cursor.moveToNext()){
            String message = cursor.getString(cursor.getColumnIndex("message"));
            mArrayList.add(message);
        }

        mListView.setAdapter(mArrayAdapter);
        cursor.close();
        databaseHelper.close();

    }
}
