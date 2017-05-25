package com.beginner.micromaster.flashcardsapp.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.beginner.micromaster.flashcardsapp.R;
import com.beginner.micromaster.flashcardsapp.data.database.DataBaseDAO;
import com.beginner.micromaster.flashcardsapp.model.Card;

/**
 * Created by praxis on 11/04/17.
 */

public class AddCardDialogFragment extends DialogFragment {
    private AddCardDialogListener listener;
    private DataBaseDAO dao;

    public static AddCardDialogFragment getInstance(){
        return new AddCardDialogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listener = (AddCardDialogListener) getActivity();
        dao = new DataBaseDAO(getActivity());
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
                String question = etQuestion.getText().toString();
                String answer = etAnswer.getText().toString();

                dao.open();
                dao.addCard(new Card(question, answer));
                dao.close();

                listener.onDialogPositiveClick();
            }
        }).setNegativeButton(getString(R.string.button_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("CANCEL", "Cancel add");
            }
        });

        return builder.create();
    }

    public interface AddCardDialogListener{
        public void onDialogPositiveClick();
    }
}
