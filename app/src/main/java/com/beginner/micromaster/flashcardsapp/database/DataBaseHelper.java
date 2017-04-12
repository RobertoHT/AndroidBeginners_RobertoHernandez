package com.beginner.micromaster.flashcardsapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by praxis on 12/04/17.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "cards.db";

    public static final String TABLE_CARDS = "cards";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_QUESTION = "question";
    public static final String COLUMN_ANSWER = "answer";

    private static final int DATABASE_VERSION = 1;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "create table "
                + TABLE_CARDS + "( "
                + COLUMN_ID + " integer primary key autoincrement, "
                + COLUMN_QUESTION + " TEXT, "
                + COLUMN_ANSWER + " TEXT "
                + ")";

        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CARDS);
        onCreate(sqLiteDatabase);
    }
}
