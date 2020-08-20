package myproject.eric.quantize;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quantitativeself.databinding.DynamicRecyclerRowBinding;

import java.util.ArrayList;
import java.util.List;

public class DynamicRecyclerViewAdapter<D extends Displayable> extends RecyclerView.Adapter<DynamicRecyclerViewAdapter<D>.ViewHolder> {
    private final RowClickListener theListener;
    private final LayoutInflater theLayoutInflater;
    private List<D> theDisplayData;
    private final boolean theMoveButtonsEnabled;

    DynamicRecyclerViewAdapter(Context aContext, RowClickListener aListener, boolean aMoveEnabled) {
        theLayoutInflater = LayoutInflater.from(aContext);
        theListener = aListener;
        theDisplayData = new ArrayList<>();
        theMoveButtonsEnabled = aMoveEnabled;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup aParent, int aViewType) {
        DynamicRecyclerRowBinding myBinding = DynamicRecyclerRowBinding.inflate(theLayoutInflater, aParent, false);
        if (!theMoveButtonsEnabled) {
            myBinding.getRoot().removeView(myBinding.downButton);
            myBinding.getRoot().removeView(myBinding.upButton);
        }
        return new ViewHolder(myBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder aHolder, int aPosition) {
        D myToDisplay = theDisplayData.get(aPosition);
        aHolder.theBinding.rowText.setText(myToDisplay.displayName());
    }

    @Override
    public int getItemCount() {
        return theDisplayData.size();
    }

    public D getItem(int aPos) {
        return theDisplayData.get(aPos);
    }

    void setDisplayData(@NonNull List<D> aDisplayData) {
        theDisplayData = aDisplayData;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        DynamicRecyclerRowBinding theBinding;

        ViewHolder(DynamicRecyclerRowBinding aBinding) {
            super(aBinding.getRoot());
            theBinding = aBinding;
            theBinding.getRoot().setOnClickListener(x -> theListener.onItemClick(x, getAdapterPosition()));
            theBinding.getRoot().setOnLongClickListener(x -> theListener.onLongPress(x, getAdapterPosition()));  //Allow edit on long press
            theBinding.deleteButton.setOnClickListener(x -> theListener.onDelete(x, getAdapterPosition()));
            if (theMoveButtonsEnabled) {
                theBinding.downButton.setOnClickListener(x -> theListener.onDownClick(x, getAdapterPosition()));
                theBinding.upButton.setOnClickListener(x -> theListener.onUpClick(x, getAdapterPosition()));
            }
        }
    }
}
