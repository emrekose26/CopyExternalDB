package com.emrekose.copyexternaldbapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.emrekose.copyexternaldb.CopyDatabase;

/**
 * Created by emrekose on 31.07.2016.
 */


public class DatabaseHelper extends CopyDatabase {

    // external db table name
    public static final String TABLE_NAME = "test_table";

    public final Context mContext;

    public DatabaseHelper(Context context, int version, String databaseName) {
        super(context, version, databaseName);
        mContext = context;
    }

    // your external db columns here
    public static class Columns implements BaseColumns{

        public static String COLUMN_MESSAGE = "message";

        public static final String[] ALL_COLUMNS = {
                _ID,
                COLUMN_MESSAGE
        };
    }


    // return all rows on table
    public Cursor getAllRows(){

        super.close();
        super.openDatabase();

        SQLiteDatabase db = super.getReadableDatabase();
        Cursor c = db.query(TABLE_NAME, new String[] {Columns.COLUMN_MESSAGE}, null , null , null , null , null);

        return c;
    }

}
