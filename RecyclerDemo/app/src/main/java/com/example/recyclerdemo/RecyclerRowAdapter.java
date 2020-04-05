package com.example.recyclerdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class RecyclerRowAdapter extends RecyclerView.Adapter<RecyclerRowAdapter.ViewHolder> {

    private List<RowData> theRowData;
    private LayoutInflater theLayoutinflater;
    private ItemClickListener theItemClickListener;

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup aParent, int aViewType) {
        View myView = theLayoutinflater.inflate(R.layout.recycler_row, aParent, false);
        return new ViewHolder(myView);
    }

    RecyclerRowAdapter(Context aContext, List<String> aData) {
            theLayoutinflater = LayoutInflater.from(aContext);
            theRowData = newArrayList();
            for (String myItem : aData) {
                theRowData.add(new RowData(myItem));
            }
    }

    @Override
    public void onBindViewHolder(ViewHolder aHolder, int aPosition) {
            String myString = theRowData.get(aPosition).getData();
            aHolder.theTextView.setText(myString);
    }

    @Override
    public int getItemCount() {
            return theRowData.size();
    }

    String getItem(int id) {
        return theRowData.get(id).getData();
    }

    //Item click count is not persisted through any lifecycle changes
    int getItemClickCount(int aId) {
        return theRowData.get(aId).getClickCount();
    }

    void setClickListener(ItemClickListener aItemClickListener) {
        this.theItemClickListener = aItemClickListener;
    }

    private static class RowData {
        private String theData;
        private int theClickCount;

        RowData(String aData) {
            theData = aData;
            theClickCount = 0;
        }

        String getData() {
            return theData;
        }

        int getClickCount() {
            return theClickCount;
        }

        void setClickCount(int aClickCount) {
            theClickCount = aClickCount;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView theTextView;

        ViewHolder(View aItemView) {
            super(aItemView);
            theTextView = aItemView.findViewById(R.id.rowText);
            aItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View aView) {
            if (theItemClickListener != null) {
                int myPosition = getAdapterPosition();
                theRowData.get(myPosition).setClickCount(getItemClickCount(myPosition) + 1);
                theItemClickListener.onItemClick(aView, myPosition);
            }
        }
    }
}