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
import com.beginner.micromaster.flashcardsapp.data.DataCard;
import com.beginner.micromaster.flashcardsapp.dialog.AddCardDialogFragment;
import com.beginner.micromaster.flashcardsapp.menu.SettingsActivity;
import com.beginner.micromaster.flashcardsapp.model.Card;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AddCardDialogFragment.AddCardDialogListener {
    private RecyclerView recyclerView;
    private CardAdapter adapter;
    private List<Card> cardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PreferenceManager.setDefaultValues(this, R.xml.settings, false);

        getViews();
        getData();
    }

    private void getViews(){
        recyclerView = (RecyclerView) findViewById(R.id.rvCards);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCardDialogFragment dialogFragment = AddCardDialogFragment.getInstance();
                dialogFragment.show(getFragmentManager(), "addCard");
            }
        });
    }

    private void getData(){
        cardList = DataCard.getCardList(this);

        adapter = new CardAdapter(this, cardList);
        recyclerView.setAdapter(adapter);
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

    @Override
    public void onDialogPositiveClick() {
        cardList.clear();
        cardList.addAll(DataCard.getCardList(this));
        adapter.notifyDataSetChanged();
    }
}
