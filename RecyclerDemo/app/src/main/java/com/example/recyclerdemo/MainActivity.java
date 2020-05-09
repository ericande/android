package com.example.recyclerdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import static com.google.common.collect.Lists.newArrayList;

public class MainActivity extends AppCompatActivity {

    private DynamicRecyclerRowAdapter theRecyclerRowAdapter;

    @Override
    protected void onCreate(Bundle aSavedInstanceState) {
        super.onCreate(aSavedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> myListData = newArrayList(getResources().getStringArray(R.array.trees));
        RecyclerView myRecyclerView = findViewById(R.id.recycleList);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        theRecyclerRowAdapter = new DynamicRecyclerRowAdapter(this, myListData);
        theRecyclerRowAdapter.setListener(this::registerClick);
        myRecyclerView.setAdapter(theRecyclerRowAdapter);
    }

    public void addItem(View aView) {
        EditText addText = findViewById(R.id.addText);
        theRecyclerRowAdapter.addItem(addText.getText().toString());
    }

    public void registerClick(View aView, int aPosition) {
        Toast.makeText(this, "Performed a click on row "
                + aPosition
                + " in the view "
                + aView.getClass(), Toast.LENGTH_SHORT).show();
    }
}

