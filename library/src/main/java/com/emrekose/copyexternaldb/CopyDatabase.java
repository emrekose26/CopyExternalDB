/*
 * Copyright (C) 2016 Emre Köse
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

public class CopyDatabase extends SQLiteOpenHelper {

    // application package name
    public static String PACKAGE_NAME;

    // database package in device
    public static String DB_PATH;

    // external database name in app/src/main/assets folder
    public static String DB_NAME;

    // application context
    public Context mContext;

    private SQLiteDatabase mDatabase;

    /**
     *
     * @param context of application context
     * @param version of external database version
     * @param databaseName of external database name in assets folder
     */
    public CopyDatabase(Context context, int version, String databaseName) {
        super(context, databaseName, null, version);

        mContext = context;
        PACKAGE_NAME = getPackageName(context);
        DB_NAME = databaseName;

        // database path in device
        DB_PATH = "/data/data/" + PACKAGE_NAME + "/databases/";
    }


    @Override
    public void onCreate(SQLiteDatabase db) {}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    @Override
    public synchronized void close() {
        if (mDatabase != null && mDatabase.isOpen())
            mDatabase.close();
        super.close();
    }

    /**
     * database creating process
     */
    public void createDatabase() throws Exception {
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

    /**
     * copy to database in device which copy assets folder database
     */
    private void copyDatabase() throws Exception {

        try {
            InputStream input = mContext.getAssets().open(DB_NAME);
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

    /**
     * check copy database process after copying to database in device
     * @return true or false
     */
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

    /**
     * copying database open process
     */
    public void openDatabase() {
        String path = DB_PATH + DB_NAME;
        mDatabase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
    }

    /**
     * for static PACKAGE_NAME variables
     * @param context of application context
     * @return application package name
     */
    public String getPackageName(Context context) {
        return context.getPackageName();
    }


}
