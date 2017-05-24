package com.beginner.micromaster.flashcardsapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.beginner.micromaster.flashcardsapp.R;
import com.beginner.micromaster.flashcardsapp.adapter.CardAdapter;
import com.beginner.micromaster.flashcardsapp.data.database.DataBaseDAO;
import com.beginner.micromaster.flashcardsapp.dialog.AddCardDialogFragment;
import com.beginner.micromaster.flashcardsapp.menu.SettingsActivity;
import com.beginner.micromaster.flashcardsapp.model.Card;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.beginner.micromaster.flashcardsapp.data.reader.JsonReader.loadJsonFromAsset;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    private DataBaseDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dao = new DataBaseDAO(this);

        getViews();
        getData();

        PreferenceManager.setDefaultValues(this, R.xml.settings, false);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCardDialogFragment dialogFragment = AddCardDialogFragment.getInstance();
                dialogFragment.show(getFragmentManager(), "addCard");
            }
        });
    }

    private void getViews(){
        recyclerView = (RecyclerView) findViewById(R.id.rvCards);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void getData(){
        List<Card> cardList = getCardListFromJson();

        cardList.addAll(getCardListFromDB());

        CardAdapter adapter = new CardAdapter(this, cardList);
        recyclerView.setAdapter(adapter);
    }

    private List<Card> getCardListFromJson(){
        List<Card> list;

        String json = loadJsonFromAsset(getApplicationContext(), "cards.json");
        Gson gson = new Gson();
        Card[] cards = gson.fromJson(json, Card[].class);
        list = new ArrayList<Card>(Arrays.asList(cards));

        return list;
    }

    private List<Card> getCardListFromDB(){
        List<Card> list;

        dao.open();
        list = dao.getAllCards();
        dao.close();

        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
