package com.emrekose.copyexternaldb;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by emrekose on 24.07.2016.
 */

public class CopyDatabaseHelper extends SQLiteOpenHelper {

    public static String PACKAGE_NAME;
    public static String DB_PATH;
    public static String DB_NAME;

    public Context context;

    private SQLiteDatabase database;

    public CopyDatabaseHelper(Context context, int version, String databaseName) {
        super(context, databaseName, null, version);
        this.context = context;
        PACKAGE_NAME = getPackageName(context);
        DB_NAME = databaseName;
        DB_PATH = "/data/data/" + PACKAGE_NAME + "/databases/";
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @Override
    public synchronized void close() {
        if (database != null && database.isOpen())
            database.close();
        super.close();
    }

    public void createDatabase() {
        boolean dbExists = checkDatabase();

        if (!dbExists) {
            this.getReadableDatabase();

            try {
                copyDatabase();
            } catch (Exception e) {
                Log.e("DB_ERROR", "createDatabase(): Could not copy DB");
                throw new Error("Could not copy DB'");
            }
        }
    }

    private void copyDatabase() {

        try {
            InputStream input = context.getAssets().open(DB_NAME);
            String outFileName = DB_PATH + DB_NAME;
            OutputStream output = new FileOutputStream(outFileName);

            byte[] buffer = new byte[1024];
            int length;

            while (((length = input.read(buffer)) > 0)) {
                output.write(buffer, 0, length);
            }

            output.flush();
            input.close();
            output.close();

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("DB_ERROR", "copyDatabase(): Could not copy DB");
        }
    }

    private boolean checkDatabase() {
        SQLiteDatabase checkDB = null;
        try {
            String path = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("DB_ERROR", "checkDatabase(): Could not open DB");
        }

        if (checkDB != null)
            checkDB.close();
        return checkDB != null ? true : false;
    }

    public void openDatabase() {
        String path = DB_PATH + DB_NAME;
        database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
    }

    public String getPackageName(Context context) {
        return context.getPackageName();
    }


}
