
package com.example.jewar.fitnessinstructor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapDataDBMgr extends SQLiteOpenHelper {

    //Declare Variables
    private static final int DB_VER = 1;
    private static final String DB_PATH = "/data/data/com.example.jewar.fitnessinstructor/databases/";
    private static final String DB_NAME = "User";
    private static final String TBL_GYMLOCATIONS = "gymLocations";

    public static final String COL_GYMID = "gymID";
    public static final String COL_GYMADDRESS = "gymAddress";
    public static final String COL_GYMAREA = "gymArea";
    public static final String COL_LATITUDE = "latitude";
    public static final String COL_LONGITUDE = "longitude";

    private final Context appContext;

    public MapDataDBMgr(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.appContext = context;
    }

    @Override

    //method for creating locations of each gym table
    public void onCreate(SQLiteDatabase db) {
        String CREATE_GYMLOCATIONS_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TBL_GYMLOCATIONS + "("
                + COL_GYMID + " INTEGER PRIMARY KEY," + COL_GYMADDRESS
                + " TEXT," + COL_GYMAREA + " TEXT," + COL_LATITUDE + " FLOAT,"
                + COL_LONGITUDE + " FLOAT" + ")";
        db.execSQL(CREATE_GYMLOCATIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TBL_GYMLOCATIONS);
            onCreate(db);
        }
    }

    public void dbCreate() throws IOException {
        boolean dbExist = dbCheck();
        if (!dbExist) {
            this.getReadableDatabase();
            try {
                copyDBFromAssets();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    //method for checking database location
    private boolean dbCheck() {
        SQLiteDatabase db = null;
        try {
            String dbPath = DB_PATH + DB_NAME;
            db = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
            db.setLocale(Locale.getDefault());
            db.setVersion(1);
        } catch (SQLiteException e) {
            Log.e("SQLHelper", "Database not Found!");
        }
        if (db != null) {
            db.close();
        }
        return db != null ? true : false;
    }

    private void copyDBFromAssets() throws IOException {
        InputStream dbInput = null;
        OutputStream dbOutput = null;
        String dbFileName = DB_PATH + DB_NAME;
        try {

            dbInput = appContext.getAssets().open(DB_NAME);
            dbOutput = new FileOutputStream(dbFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = dbInput.read(buffer)) > 0) {
                dbOutput.write(buffer, 0, length);
            }
            dbOutput.flush();
            dbOutput.close();
            dbInput.close();
        } catch (IOException e) {
            throw new Error("Problems copying DB!");
        }
    }

    //mehtod of each gym entry
    public void addaMapGymLocationsEntry(MapData aMapGymLocations) {
        ContentValues values = new ContentValues();
        values.put(COL_GYMID, aMapGymLocations.getGymID());
        values.put(COL_GYMADDRESS, aMapGymLocations.getGymAddress());
        values.put(COL_GYMAREA, aMapGymLocations.getGymArea());
        values.put(COL_LATITUDE, aMapGymLocations.getLatitude());
        values.put(COL_LONGITUDE, aMapGymLocations.getLongitude());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TBL_GYMLOCATIONS, null, values);
        db.close();
    }

    public MapData getMapGymLocationsEntry(String aMapGymLocationsEntry) {
        String query = "Select * FROM " + TBL_GYMLOCATIONS + " WHERE " + COL_GYMID + " =  \"" + aMapGymLocationsEntry + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        MapData MapDataEntry = new MapData();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            MapDataEntry.setGymID(Integer.parseInt(cursor.getString(0)));
            MapDataEntry.setGymAddress(cursor.getString(1));
            MapDataEntry.setGymArea(cursor.getString(2));
            MapDataEntry.setLatitude(Float.parseFloat(cursor.getString(3)));
            MapDataEntry.setLongitude(Float.parseFloat(cursor.getString(4)));
            cursor.close();
        } else {
            MapDataEntry = null;
        }
        db.close();
        return MapDataEntry;
    }

    public List<MapData> allMapData() {
        String query = "Select * FROM " + TBL_GYMLOCATIONS;
        List<MapData> MapDataList = new ArrayList<MapData>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                MapData MapDataEntry = new MapData();
                MapDataEntry.setGymID(Integer.parseInt(cursor.getString(0)));
                MapDataEntry.setGymAddress(cursor.getString(1));
                MapDataEntry.setGymArea(cursor.getString(2));
                MapDataEntry.setLatitude(Float.parseFloat(cursor.getString(3)));
                MapDataEntry.setLongitude(Float.parseFloat(cursor.getString(4)));
                MapDataList.add(MapDataEntry);
                cursor.moveToNext();
            }
        } else {
            MapDataList.add(null);
        }
        db.close();
        return MapDataList;
    }
}
