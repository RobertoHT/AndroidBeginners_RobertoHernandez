package com.beginner.micromaster.flashcardsapp.data;

import android.content.Context;

import com.beginner.micromaster.flashcardsapp.data.database.DataBaseDAO;
import com.beginner.micromaster.flashcardsapp.model.Card;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.beginner.micromaster.flashcardsapp.data.reader.JsonReader.loadJsonFromAsset;

/**
 * Created by praxis on 24/05/17.
 */

public class DataCard {
    public static List<Card> getCardList(Context context){
        List<Card> cardList;

        cardList = getCardListFromJson(context);
        cardList.addAll(getCardListFromDB(context));

        return cardList;
    }

    private static List<Card> getCardListFromJson(Context context){
        List<Card> list;

        String json = loadJsonFromAsset(context.getApplicationContext(), "cards.json");
        Gson gson = new Gson();
        Card[] cards = gson.fromJson(json, Card[].class);
        list = new ArrayList<>(Arrays.asList(cards));

        return list;
    }

    private static List<Card> getCardListFromDB(Context context){
        List<Card> list;
        DataBaseDAO dao = new DataBaseDAO(context);

        dao.open();
        list = dao.getAllCards();
        dao.close();

        return list;
    }
}
