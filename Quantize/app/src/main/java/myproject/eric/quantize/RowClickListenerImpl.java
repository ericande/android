package myproject.eric.quantize;

import android.view.View;

class RowClickListenerImpl implements RowClickListener {
    @Override
    public void onItemClick(View aView, int aPosition) {
        //NO-OP
    }

    @Override
    public void onDownClick(View aView, int aPosition) {
        //NO-OP
    }

    @Override
    public void onUpClick(View aView, int aPosition) {
        //NO-OP
    }

    @Override
    public void onDelete(View aView, int aPosition) {
        //NO-OP
    }

    @Override
    public boolean onLongPress(View aView, int aPosition) {
        return false;
    }
}
