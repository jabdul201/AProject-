package com.example.jewar.fitnessinstructor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jewar
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    //create database version
    private static final int DATABASE_VERSION = 1;

    //create database name
    private static final String DATABASE_NAME = "Users",
    TABLE_USERS = "users",
    KEY_ID = "id",
    KEY_NAME = "name",
    KEY_EMAIL = "email",
    KEY_PHONE ="phone",
    KEY_ADDRESS = "address";

    //create constructor

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    //create method  for creating database
    public void onCreate (SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + TABLE_USERS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT," + KEY_EMAIL + " TEXT," + KEY_PHONE + " TEXT," + KEY_ADDRESS + " TEXT)");
    }

    @Override
    //create method for updating database
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        onCreate(db);
    }

    //Create user
    public void createUser(User user){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, user.getName());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_PHONE, user.getPhone());
        values.put(KEY_ADDRESS, user.getAddress());

        //insert into table
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    //return user
    public User getUser(int id){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[] {KEY_ID, KEY_NAME,KEY_EMAIL, KEY_PHONE, KEY_ADDRESS}, KEY_ID + "=?", new String [] {String.valueOf(id) }, null, null, null);
        if (cursor != null)
        cursor.moveToFirst();

        User user = new User(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        db.close();
        cursor.close();
        return user;
    }

    //delete users
    public void deleteUser(User user){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_USERS, KEY_ID + "=?", new String[] {String.valueOf(user.getId()) });
        db.close();
    }

    //query the database
    public int getUserCount(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS, null);
        int count = cursor.getCount();
        db.close();
        cursor.close();

        return count;
    }

    public int updateUser(User user){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_NAME, user.getName());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_PHONE, user.getPhone());
        values.put(KEY_ADDRESS, user.getAddress());

        return db.update(TABLE_USERS, values, KEY_ID + "=?", new String[]{String.valueOf(user.getId())});

    }

    public  List<User> getAllUsers(){
        List<User> Users = new ArrayList<User>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS,null);
        if (cursor.moveToFirst()){

            do {
                Users.add(new User(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)));
            }
           while  (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return Users;
    }
}
