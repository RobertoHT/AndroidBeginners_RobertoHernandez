package com.beginner.micromaster.flashcardsapp.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.beginner.micromaster.flashcardsapp.R;

public class AnswerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        TextView tvQuestion = (TextView) findViewById(R.id.tvQuestion);
        TextView tvAnswer = (TextView) findViewById(R.id.tvAnswer);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            tvQuestion.setText(extras.getString("question"));
            tvAnswer.setText(extras.getString("answer"));
        }
    }

}
