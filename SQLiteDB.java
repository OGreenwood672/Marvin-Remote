package com.ogreenwood.discord_music;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteDB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "USER_BUTTONS";
    public static final String BUTTON_TABLE_NAME = "button";
    public static final String BUTTON_COLUMN_ID = "_id";
    public static final String BUTTON_COLUMN_TITLE = "title";
    public static final String BUTTON_COLUMN_URL = "URL";
    public static final String BUTTON_COLUMN_AUTOSHUFFLE = "autoshuffle";

    public SQLiteDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + BUTTON_TABLE_NAME + " (" +
                BUTTON_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                BUTTON_COLUMN_TITLE + " TEXT, " +
                BUTTON_COLUMN_URL + " TEXT, " +
                BUTTON_COLUMN_AUTOSHUFFLE + " TEXT" + ")");

    }

    public long addNewButton(SQLiteDatabase database, String title, String url, boolean autoshuffle) {


        ContentValues values = new ContentValues();
        values.put(SQLiteDB.BUTTON_COLUMN_TITLE, title);
        values.put(SQLiteDB.BUTTON_COLUMN_URL, url);
        values.put(SQLiteDB.BUTTON_COLUMN_AUTOSHUFFLE, autoshuffle);
        long newRowId = database.insert(SQLiteDB.BUTTON_TABLE_NAME, null, values);

        Log.e("DATABASE OPERATION", BUTTON_COLUMN_TITLE + " added");

        return newRowId;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BUTTON_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

}