package com.beginner.micromaster.flashcardsapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by praxis on 11/04/17.
 */

public class AddCardDialogFragment extends DialogFragment {
    String cadena;

    public static AddCardDialogFragment getInstance(){
        AddCardDialogFragment fragment = new AddCardDialogFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_new_question, null);
        final EditText etQuestion = (EditText) view.findViewById(R.id.input_question);
        final EditText etAnswer = (EditText) view.findViewById(R.id.input_answer);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setTitle(getString(R.string.title_add_card_dialog));
        builder.setPositiveButton(getString(R.string.button_add), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                cadena = etQuestion.getText().toString() + " - " + etAnswer.getText().toString();
                Log.d("ADD", cadena);
            }
        }).setNegativeButton(getString(R.string.button_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("CANCEL", "Cancel create");
            }
        });

        return builder.create();
    }
}
