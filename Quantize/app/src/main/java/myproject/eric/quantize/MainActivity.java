package myproject.eric.quantize;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.quantitativeself.databinding.ActivityMainBinding;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import myproject.eric.quantize.model.Row;

public class MainActivity extends AppCompatActivity {
    public static final String ROW_ID_MSG = "myproject.eric.quantize.ROW_ID";
    public static final String ROW_NAME_MSG = "myproject.eric.quantize.ROW_NAME";
    public static final String MATCH_WHITESPACE = "\\s*";
    protected DynamicRecyclerViewAdapter<Row> theRecyclerRowAdapter;
    protected RowViewModel theViewModel;
    private ActivityMainBinding theBinding;

    @Override
    protected void onCreate(Bundle aSavedInstanceState) {
        super.onCreate(aSavedInstanceState);
        theBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(theBinding.getRoot());
        theViewModel = getDefaultViewModelProviderFactory().create(RowViewModel.class);
        theViewModel.getRows().observe(this, recycleAdapterUpdateObserver());

        theBinding.recycleList.setLayoutManager(new LinearLayoutManager(this));
        theRecyclerRowAdapter = new DynamicRecyclerViewAdapter<>(this, new MainActivityClickHandler(this), true);
        theBinding.recycleList.setAdapter(theRecyclerRowAdapter);
    }

    public void fab(View aView) {
        showAddDialog(null);
    }

    protected void showAddDialog(@Nullable Row aUpdate) {
        EditText myEditText = new EditText(this);
        myEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        String myBtnText = aUpdate != null ? "UPDATE" : "ADD";
        String myTitleText = aUpdate != null ? "Edit item:" : "Add item:";
        DialogInterface.OnClickListener myDoneListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface aDialog, int aWhich) {
                String myAddString = myEditText.getText().toString();
                if (myAddString.matches(MATCH_WHITESPACE)) {
                    Toast.makeText(MainActivity.this, "Invalid word not added!", Toast.LENGTH_SHORT).show();
                } else {
                    String msg;
                    if (aUpdate == null) {
                        msg = "Added new row called " + myAddString;
                        theViewModel.add(new Row(myAddString, addPos()));
                    } else {
                        msg = "Renamed row from " + aUpdate.name + " to " + myAddString;
                        theViewModel.update(aUpdate);
                        aUpdate.name = myAddString;
                    }
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }
        };
        AlertDialog myDialog = new AlertDialog.Builder(this)
                .setTitle(myTitleText)
                .setView(myEditText)
                .setPositiveButton(myBtnText, myDoneListener)
                .setNegativeButton("CANCEL", (x, y) -> {
                }).create();
        myEditText.setOnEditorActionListener((aView, aAction, aEvent) -> {
            if (aAction == EditorInfo.IME_ACTION_DONE) {
                myDoneListener.onClick(null, 0);
                myDialog.dismiss();
            }
            return false;
        });
        myDialog.show();
    }

    private int addPos() {
        return theRecyclerRowAdapter.getItemCount() == 0 ? 0
                : theRecyclerRowAdapter.getItem(theRecyclerRowAdapter.getItemCount() - 1).pos + 1;
    }

    private Observer<List<Row>> recycleAdapterUpdateObserver() {
        return new Observer<List<Row>>() {
            @Override
            public void onChanged(List<Row> aRows) {
                theRecyclerRowAdapter.setDisplayData(aRows);
            }
        };
    }
}

