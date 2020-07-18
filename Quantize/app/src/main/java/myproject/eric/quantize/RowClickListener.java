package myproject.eric.quantize;

import android.view.View;

interface RowClickListener {
    void onItemClick(View aView, int aPosition);

    void onDownClick(View aView, int aPosition);

    void onUpClick(View aView, int aPosition);

    void onDelete(View aView, int aPosition);

    boolean onLongPress(View aView, int aPosition);
}
