package myproject.eric.quantize;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.quantitativeself.R;
import com.example.quantitativeself.databinding.ActivityRowDisplayBinding;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

import myproject.eric.quantize.model.AppDatabase;
import myproject.eric.quantize.model.Entry;

public class RowDisplayActivity extends AppCompatActivity {
    DynamicRecyclerViewAdapter<Entry> theRecyclerAdapter;
    private ActivityRowDisplayBinding theBinding;
    AppDatabase theDatabase;
    List<Entry> theEntries;
    Queue<String> theSuggestions;
    int theRowId;
    Button[] theButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        theBinding = ActivityRowDisplayBinding.inflate(getLayoutInflater());
        setContentView(theBinding.getRoot());
        theButtons = new Button[] {theBinding.add1, theBinding.add2, theBinding.add3, theBinding.add4, theBinding.add5};

        theRecyclerAdapter = new DynamicRecyclerViewAdapter<>(this, makeListener(), false);
        theBinding.entryRv.setLayoutManager(new LinearLayoutManager(this));
        theBinding.entryRv.setAdapter(theRecyclerAdapter);

        Intent myIntent = getIntent();
        theRowId = myIntent.getIntExtra(MainActivity.ROW_ID_MSG, -1);
        theBinding.header.setText(myIntent.getStringExtra(MainActivity.ROW_NAME_MSG));
        theDatabase = AppDatabase.getInstance(getApplicationContext());
        theEntries = theDatabase.getEntryDao().getAllForRow(theRowId);
        theRecyclerAdapter.setDisplayData(theEntries);

        startingSuggestions();
    }

    private void populateAddButtons() {
        Iterator<String> mySuggestions = theSuggestions.iterator();
        for (Button b : theButtons) {
            b.setText(mySuggestions.next());
        }
    }

    private void startingSuggestions() {
        String[] myAddButtonTexts = getResources().getStringArray(R.array.default_entries);
        theSuggestions = new ArrayDeque<>(Arrays.asList(myAddButtonTexts));
        for (Entry myEntry : theEntries) {
            addSuggestion(myEntry);
        }
        populateAddButtons();
    }

    private void addSuggestion(Entry aEntry) {
        if (!theSuggestions.contains(aEntry.data)) {
            theSuggestions.add(aEntry.data);
        }
        if (theSuggestions.size() > theButtons.length) {
            theSuggestions.remove();
        }
    }

    public void add(View aView) {
        String myEntryVal = ((Button) aView).getText().toString();
        addEntry(myEntryVal);
    }

    public void addCustom(View aView) {
        EditText myEditText = new EditText(this);
        myEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        new AlertDialog.Builder(this)
                .setPositiveButton("ADD", (v, i) -> {
                    String myAddText = myEditText.getText().toString();
                    if (myAddText.matches(MainActivity.MATCH_WHITESPACE)) {
                        Toast.makeText(this, "Blank entry not added", Toast.LENGTH_SHORT).show();
                    } else {
                        addEntry(myAddText);
                    }
                })
                .setNegativeButton("CANCEL", (v, i) -> {
                })
                .setView(myEditText)
                .create()
                .show();
    }

    private void addEntry(String aS) {
        Entry myEntry = new Entry();
        myEntry.data = aS;
        myEntry.rowId = theRowId;
        theEntries.add(myEntry);
        theRecyclerAdapter.notifyItemInserted(theEntries.size()-1);
        AppDatabase.write(() -> theDatabase.getEntryDao().insert(myEntry));
        Toast.makeText(this, "Added new entry: " + aS, Toast.LENGTH_SHORT).show();
        addSuggestion(myEntry);
        populateAddButtons();
    }

    private RowClickListener makeListener() {
        return new RowClickListenerImpl() {
            @Override
            public void onDelete(View aView, int aPosition) {
                Entry myEntry = theRecyclerAdapter.getItem(aPosition);
                theEntries.remove(myEntry);
                theRecyclerAdapter.notifyItemRemoved(aPosition);
                AppDatabase.write(() -> theDatabase.getEntryDao().delete(myEntry));
            }
        };
    }
}
