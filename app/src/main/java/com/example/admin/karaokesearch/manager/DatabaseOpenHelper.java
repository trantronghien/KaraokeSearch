package com.example.admin.karaokesearch.manager;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.util.Log;


import com.example.admin.karaokesearch.models.DaoMaster;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by admin on 3/10/2017.
 */

public class DatabaseOpenHelper extends DaoMaster.OpenHelper {
    private Context context;
    private SQLiteDatabase sqliteDatabase;
    private final String TAG_LOG = "OpenHelper";
    private onCopyDatabaseFinished databaseFinished;

    public static final String DATASE_NAME = "Karaoke";
    private static final String DB_PATH = "/data/data/com.example.admin.karaokesearch/databases/";


    public DatabaseOpenHelper(Context context, String name ,SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
        this.context = context;
        try {
            createDataBase();
        } catch (Exception ioe) {
            throw new Error("Unable to create database");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /** Open Database for Use */
    public void openDatabase() {
        String databasePath = DB_PATH + DATASE_NAME;
        sqliteDatabase = SQLiteDatabase.openDatabase(databasePath, null,
                (SQLiteDatabase.OPEN_READWRITE));
    }

    /** Close Database after use */
    @Override
    public synchronized void close() {
        if ((sqliteDatabase != null) && sqliteDatabase.isOpen()) {
            sqliteDatabase.close();
        }
        super.close();
    }

    /** Get database instance for use */
    public SQLiteDatabase getDatabaseInstance() {
        return sqliteDatabase;
    }

    /** Create new database if not present */
    public void createDataBase() {
        SQLiteDatabase sqliteDatabase ;
        if (databaseExists()) {
            /* Check for Upgrade */

        } else {
            /* Database does not exists create blank database */
            sqliteDatabase = this.getReadableDatabase();
            sqliteDatabase.close();
            copyDataBase();
        }
    }

    /** Check Database if it exists */
    public boolean databaseExists() {
        SQLiteDatabase sqliteDatabase = null;
        try {
            String databasePath = DB_PATH + DATASE_NAME;
            if (!new File(databasePath).exists())  return false;

            sqliteDatabase = SQLiteDatabase.openDatabase(databasePath, null,
                    SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }

        if (sqliteDatabase != null) {
            sqliteDatabase.close();
        }
        return sqliteDatabase != null ? true : false;
    }

    /**
     * Copy existing database file in system
     */
    public void copyDataBase() {
        int length;
        byte[] buffer = new byte[1024];
        String databasePath = DB_PATH + DATASE_NAME;

        try {
            InputStream databaseInputFile = this.context.getAssets().open(DATASE_NAME);
            OutputStream databaseOutputFile = new FileOutputStream(databasePath);
            while ((length = databaseInputFile.read(buffer)) > 0) {
                databaseOutputFile.write(buffer, 0, length);
                databaseOutputFile.flush();
            }
            databaseInputFile.close();
            databaseOutputFile.close();
            Log.i(TAG_LOG , "CopyDB: copy database success");

        } catch (IOException e) {
            Log.e(TAG_LOG , "CopyDB: copy database fail");
            e.printStackTrace();
        }
    }
    public void processCopy(){
        new MyTask().execute();
    }

    public void setOnCopyDatabaseFinished(onCopyDatabaseFinished databaseFinished){
        this.databaseFinished = databaseFinished;
    }

    public interface onCopyDatabaseFinished{
        void onCopyFinished(boolean isFinished);
    }

    public class MyTask extends AsyncTask<Void , Void , Integer>{

        @Override
        protected Integer doInBackground(Void... params) {
            copyDataBase();
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            databaseFinished.onCopyFinished(true);
        }
    }
}
