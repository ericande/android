package com.example.dummyapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.os.Bundle;

public class HelloActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
    }

    public void onClick(View view) {
        EditText nameText = (EditText) findViewById(R.id.nameText);
        String name = nameText.getText().toString();
        Toast.makeText(this,"Hello, " + name + "!",Toast.LENGTH_LONG).show();
    }
}
