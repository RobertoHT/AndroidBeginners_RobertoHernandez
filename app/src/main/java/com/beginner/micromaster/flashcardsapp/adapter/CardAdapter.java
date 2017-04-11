package com.beginner.micromaster.flashcardsapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beginner.micromaster.flashcardsapp.R;

import java.util.List;

/**
 * Created by praxis on 11/04/17.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder>{
    private List<String> questionList;

    public CardAdapter(List<String> questionList) {
        this.questionList = questionList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtQuestion.setText(questionList.get(position));
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtQuestion;

        ViewHolder(View itemView) {
            super(itemView);
            txtQuestion = (TextView) itemView.findViewById(R.id.txtQuestion);
        }
    }
}
