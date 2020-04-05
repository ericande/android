package com.example.recyclerdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import static com.google.common.collect.Lists.newArrayList;

public class MainActivity extends AppCompatActivity implements ItemClickListener {

    private RecyclerRowAdapter theRecyclerRowAdapter;

    @Override
    protected void onCreate(Bundle aSavedInstanceState) {
        super.onCreate(aSavedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> myListData = newArrayList(getResources().getStringArray(R.array.trees));

        RecyclerView myRecyclerView = findViewById(R.id.recycleList);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        theRecyclerRowAdapter = new RecyclerRowAdapter(this, myListData);
        theRecyclerRowAdapter.setClickListener(this);
        myRecyclerView.setAdapter(theRecyclerRowAdapter);
    }

    @Override
    public void onItemClick(View aView, int aPosition) {
        String myToastText = theRecyclerRowAdapter.getItem(aPosition)
                + " trees planted: "
                + theRecyclerRowAdapter.getItemClickCount(aPosition);
        Toast.makeText(this, myToastText, Toast.LENGTH_SHORT).show();
    }
}

