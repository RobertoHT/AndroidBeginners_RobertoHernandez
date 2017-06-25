package com.beginner.micromaster.flashcardsapp.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by praxis on 12/04/17.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "cards.db";

    private static final int DATABASE_VERSION = 1;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "create table "
                + DataContract.TodoEntry.TABLE_CARDS + "( "
                + DataContract.TodoEntry.COLUMN_ID + " integer primary key autoincrement, "
                + DataContract.TodoEntry.COLUMN_QUESTION + " TEXT, "
                + DataContract.TodoEntry.COLUMN_ANSWER + " TEXT "
                + ")";

        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DataContract.TodoEntry.TABLE_CARDS);
        onCreate(sqLiteDatabase);
    }
}
