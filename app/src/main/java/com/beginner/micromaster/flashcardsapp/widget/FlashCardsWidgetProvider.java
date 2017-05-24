package com.beginner.micromaster.flashcardsapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.beginner.micromaster.flashcardsapp.R;
import com.beginner.micromaster.flashcardsapp.data.database.DataBaseDAO;
import com.beginner.micromaster.flashcardsapp.model.Card;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.beginner.micromaster.flashcardsapp.data.reader.JsonReader.loadJsonFromAsset;

/**
 * Created by praxis on 18/05/17.
 */

public class FlashCardsWidgetProvider extends AppWidgetProvider {
    private static final String MY_PREFERENCE = "MyPreference";
    private static final String KEY_ACTIVE = "active";
    private static final String KEY_QUESTION = "question";
    private static final String KEY_ANSWER = "answer";
    private static final String BUTTON_SEE = "buttonSee";
    private static final String BUTTON_NEXT = "buttonNext";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for(int x=0; x<appWidgetIds.length; x++){
            int appWidgetId = appWidgetIds[x];

            updateWidget(context, appWidgetManager, appWidgetId);
        }
    }

    private void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId){
        Card card = getCard(context);

        Intent intentSee = new Intent(context, FlashCardsWidgetProvider.class);
        intentSee.setAction(BUTTON_SEE);
        intentSee.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        PendingIntent pendingIntentSee = PendingIntent.getBroadcast(context, 0, intentSee, 0);

        Intent intentNext = new Intent(context, FlashCardsWidgetProvider.class);
        intentNext.setAction(BUTTON_NEXT);
        intentNext.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        PendingIntent pendingIntentNext = PendingIntent.getBroadcast(context, 0, intentNext, 0);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        views.setTextViewText(R.id.widget_text, card.getQuestion());
        views.setOnClickPendingIntent(R.id.widget_btn_see, pendingIntentSee);
        views.setOnClickPendingIntent(R.id.widget_btn_next, pendingIntentNext);

        setCardPreference(context, card.getQuestion(), card.getAnswer());
        setActivatePreference(context, true);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        int widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        if(intent.getAction().equals(BUTTON_SEE)){
            boolean active = getActivatePreference(context);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            if(active){
                views.setTextViewText(R.id.widget_text, getCardPreference(context, true));
                views.setTextViewText(R.id.widget_btn_see, context.getString(R.string.widget_button_question));
            }else{
                views.setTextViewText(R.id.widget_text, getCardPreference(context, false));
                views.setTextViewText(R.id.widget_btn_see, context.getString(R.string.widget_button_answer));
            }

            setActivatePreference(context, !active);

            appWidgetManager.updateAppWidget(widgetId, views);
        }
        else if (intent.getAction().equals(BUTTON_NEXT)){
            updateWidget(context, appWidgetManager, widgetId);
        }
    }

    private void setCardPreference(Context context, String question, String answer){
        SharedPreferences preferences = context.getSharedPreferences(MY_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_QUESTION, question);
        editor.putString(KEY_ANSWER, answer);
        editor.apply();
    }

    private String getCardPreference(Context context, boolean activate){
        SharedPreferences preferences = context.getSharedPreferences(MY_PREFERENCE, Context.MODE_PRIVATE);
        if(activate){
            return preferences.getString(KEY_ANSWER, "");
        }else{
            return preferences.getString(KEY_QUESTION, "");
        }
    }

    private void setActivatePreference(Context context, boolean activate){
        SharedPreferences preferences = context.getSharedPreferences(MY_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_ACTIVE, activate);
        editor.apply();
    }

    private boolean getActivatePreference(Context context){
        SharedPreferences preferences = context.getSharedPreferences(MY_PREFERENCE, Context.MODE_PRIVATE);
        return preferences.getBoolean(KEY_ACTIVE, false);
    }

    private Card getCard(Context context){
        List<Card> cardList;

        cardList = getCardListFromJson(context);
        cardList.addAll(getCardListFromDB(context));

        Random random = new Random();
        int x = random.nextInt(cardList.size());

        return cardList.get(x);
    }

    private List<Card> getCardListFromJson(Context context){
        List<Card> list;

        String json = loadJsonFromAsset(context.getApplicationContext(), "cards.json");
        Gson gson = new Gson();
        Card[] cards = gson.fromJson(json, Card[].class);
        list = new ArrayList<>(Arrays.asList(cards));

        return list;
    }

    private List<Card> getCardListFromDB(Context context){
        List<Card> list;

        DataBaseDAO dao = new DataBaseDAO(context);
        dao.open();
        list = dao.getAllCards();
        dao.close();

        return list;
    }
}
