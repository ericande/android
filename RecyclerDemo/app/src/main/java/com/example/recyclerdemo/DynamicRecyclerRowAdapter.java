package com.example.recyclerdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DynamicRecyclerRowAdapter extends RecyclerView.Adapter<DynamicRecyclerRowAdapter.ViewHolder> {
    private ItemClickListener theListener;
    private LayoutInflater theLayoutinflater;
    private List<String> theRowData;

    DynamicRecyclerRowAdapter(Context aContext, List<String> aData) {
        theLayoutinflater = LayoutInflater.from(aContext);
        theRowData = new ArrayList<>();
        theRowData.addAll(aData);
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup aParent, int aViewType) {
        View myView = theLayoutinflater.inflate(R.layout.dynamic_recycler_row, aParent, false);
        return new ViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder aHolder, int aPosition) {
        String myString = theRowData.get(aPosition);
        aHolder.theTextView.setText(myString);
    }

    @Override
    public int getItemCount() {
        return theRowData.size();
    }

    void setListener(ItemClickListener aListener) {
        theListener = aListener;
    }

    void addItem(@NonNull String aS) {
        theRowData.add(aS);
        notifyItemInserted(theRowData.size() - 1);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView theTextView;

        ViewHolder(View aItemView) {
            super(aItemView);
            theTextView = aItemView.findViewById(R.id.rowText);
            aItemView.setOnClickListener(this);
            aItemView.findViewById(R.id.downButton).setOnClickListener(this::moveDown);
            aItemView.findViewById(R.id.upButton).setOnClickListener(this::moveUp);
            aItemView.findViewById(R.id.deleteButton).setOnClickListener(this::delete);
        }

        @Override
        public void onClick(View aView) {
            if (theListener != null) {
                int myPosition = getAdapterPosition();
                theListener.onItemClick(aView, myPosition);
            }
        }

        void moveDown(View aView) {
            int myPosition = getAdapterPosition();
            if (getAdapterPosition() != getItemCount() - 1) {
                String tmp = theRowData.get(myPosition + 1);
                theRowData.set(myPosition + 1, theRowData.get(myPosition));
                theRowData.set(myPosition, tmp);
                notifyItemMoved(myPosition, myPosition + 1);
            }
        }

        void moveUp(View aView) {
            int myPosition = getAdapterPosition();
            if (getAdapterPosition() != 0) {
                String tmp = theRowData.get(myPosition - 1);
                theRowData.set(myPosition - 1, theRowData.get(myPosition));
                theRowData.set(myPosition, tmp);
                notifyItemMoved(myPosition, myPosition - 1);
            }
        }

        void delete(View aView) {
            int myPosition = getAdapterPosition();
            theRowData.remove(myPosition);
            notifyItemRemoved(myPosition);
        }
    }
}
