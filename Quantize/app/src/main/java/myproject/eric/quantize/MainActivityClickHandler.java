package myproject.eric.quantize;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import myproject.eric.quantize.model.Row;

class MainActivityClickHandler implements RowClickListener {
    private MainActivity theActivity;

    public MainActivityClickHandler(MainActivity aActivity) {
        theActivity = aActivity;
    }

    @Override
    public void onItemClick(View aView, int aPosition) {
        if (aPosition != RecyclerView.NO_POSITION) {
            Row myRow = theActivity.theRecyclerRowAdapter.getItem(aPosition);
            Intent myIntent = new Intent(theActivity, RowDisplayActivity.class);
            myIntent.putExtra(MainActivity.ROW_ID_MSG, myRow.uid);
            myIntent.putExtra(MainActivity.ROW_NAME_MSG, myRow.displayName());
            theActivity.startActivity(myIntent);
        }
    }

    @Override
    public void onDownClick(View aView, int aPosition) {
        if (aPosition != theActivity.theRecyclerRowAdapter.getItemCount() - 1
                && aPosition != RecyclerView.NO_POSITION) {
            theActivity.theViewModel.swap(theActivity.theRecyclerRowAdapter.getItem(aPosition),
                    theActivity.theRecyclerRowAdapter.getItem(aPosition + 1));
        }
    }

    @Override
    public void onUpClick(View aView, int aPosition) {
        if (aPosition != 0 && aPosition != RecyclerView.NO_POSITION) {
            theActivity.theViewModel.swap(theActivity.theRecyclerRowAdapter.getItem(aPosition),
                    theActivity.theRecyclerRowAdapter.getItem(aPosition - 1));
        }
    }

    @Override
    public void onDelete(View aView, int aPosition) {
        if (aPosition != RecyclerView.NO_POSITION) {
            theActivity.theViewModel.remove(theActivity.theRecyclerRowAdapter.getItem(aPosition));
        }
    }

    @Override
    public boolean onLongPress(View aView, int aPosition) {
        if (aPosition != RecyclerView.NO_POSITION) {
            theActivity.showAddDialog(theActivity.theRecyclerRowAdapter.getItem(aPosition));
        }
        return true;
    }
}
