package com.example.dummyapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
