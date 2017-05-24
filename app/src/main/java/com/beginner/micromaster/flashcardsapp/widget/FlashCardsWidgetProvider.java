package com.beginner.micromaster.flashcardsapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.beginner.micromaster.flashcardsapp.R;
import com.beginner.micromaster.flashcardsapp.data.DataCard;
import com.beginner.micromaster.flashcardsapp.model.Card;
import com.beginner.micromaster.flashcardsapp.util.Constants;

import java.util.List;
import java.util.Random;

/**
 * Created by praxis on 18/05/17.
 */

public class FlashCardsWidgetProvider extends AppWidgetProvider {
    private String KEY_MY_PREFERENCE = Constants.KEY_WIDGET_MY_PREFERENCE;
    private String KEY_ACTIVE = Constants.KEY_WIDGET_ACTIVE;
    private String KEY_QUESTION = Constants.KEY_WIDGET_QUESTION;
    private String KEY_ANSWER = Constants.KEY_WIDGET_ANSWER;
    private String KEY_BUTTON_SEE = Constants.KEY_WIDGET_BUTTON_SEE;
    private String KEY_BUTTON_NEXT = Constants.KEY_WIDGET_BUTTON_NEXT;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId:appWidgetIds){
            updateWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        int widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        if(intent.getAction().equals(KEY_BUTTON_SEE)){
            seeQuestionAnswer(context, appWidgetManager, widgetId);
        }
        else if (intent.getAction().equals(KEY_BUTTON_NEXT)){
            updateWidget(context, appWidgetManager, widgetId);
        }
    }

    private void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId){
        Card card = getCard(context);

        Intent intentSee = new Intent(context, FlashCardsWidgetProvider.class);
        intentSee.setAction(KEY_BUTTON_SEE);
        intentSee.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        PendingIntent pendingIntentSee = PendingIntent.getBroadcast(context, 0, intentSee, 0);

        Intent intentNext = new Intent(context, FlashCardsWidgetProvider.class);
        intentNext.setAction(KEY_BUTTON_NEXT);
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

    private void seeQuestionAnswer(Context context, AppWidgetManager appWidgetManager, int appWidgetId){
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

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private void setCardPreference(Context context, String question, String answer){
        SharedPreferences preferences = context.getSharedPreferences(KEY_MY_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_QUESTION, question);
        editor.putString(KEY_ANSWER, answer);
        editor.apply();
    }

    private String getCardPreference(Context context, boolean activate){
        SharedPreferences preferences = context.getSharedPreferences(KEY_MY_PREFERENCE, Context.MODE_PRIVATE);
        if(activate){
            return preferences.getString(KEY_ANSWER, "");
        }else{
            return preferences.getString(KEY_QUESTION, "");
        }
    }

    private void setActivatePreference(Context context, boolean activate){
        SharedPreferences preferences = context.getSharedPreferences(KEY_MY_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_ACTIVE, activate);
        editor.apply();
    }

    private boolean getActivatePreference(Context context){
        SharedPreferences preferences = context.getSharedPreferences(KEY_MY_PREFERENCE, Context.MODE_PRIVATE);
        return preferences.getBoolean(KEY_ACTIVE, false);
    }

    private Card getCard(Context context){
        List<Card> cardList;

        cardList = DataCard.getCardList(context);

        Random random = new Random();
        int x = random.nextInt(cardList.size());

        return cardList.get(x);
    }
}
