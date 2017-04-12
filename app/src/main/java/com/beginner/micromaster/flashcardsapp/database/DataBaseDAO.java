package com.beginner.micromaster.flashcardsapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.beginner.micromaster.flashcardsapp.model.Card;

import java.util.ArrayList;
import java.util.List;

import static com.beginner.micromaster.flashcardsapp.database.DataBaseHelper.COLUMN_ANSWER;
import static com.beginner.micromaster.flashcardsapp.database.DataBaseHelper.COLUMN_QUESTION;
import static com.beginner.micromaster.flashcardsapp.database.DataBaseHelper.TABLE_CARDS;

/**
 * Created by praxis on 12/04/17.
 */

public class DataBaseDAO {
    private DataBaseHelper dataBaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    public DataBaseDAO(Context context) {
        dataBaseHelper = new DataBaseHelper(context);
    }

    public void open() throws SQLiteException{
        sqLiteDatabase = dataBaseHelper.getWritableDatabase();
    }

    public void close(){
        dataBaseHelper.close();
    }

    public void addCard(Card card){
        ContentValues values = new ContentValues();
        values.put(COLUMN_QUESTION, card.getQuestion());
        values.put(COLUMN_ANSWER, card.getAnswer());

        int result = (int) sqLiteDatabase.insert(TABLE_CARDS, null, values);
        Log.d("insert","result: " + result);
    }

    public List<Card> getAllCards(){
        List<Card> cardList = new ArrayList<Card>();
        Cursor cursor = sqLiteDatabase.query(TABLE_CARDS, null, null, null, null, null, null);

        if(cursor.moveToFirst()){
            do{
                String question = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_QUESTION));
                String answer = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ANSWER));

                cardList.add(new Card(question, answer));
            }while (cursor.moveToNext());
        }

        return cardList;
    }
}
