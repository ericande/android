package com.example.graphviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class MainActivity extends AppCompatActivity {

    private LineGraphSeries<DataPoint> theSeries;

    @Override
    protected void onCreate(Bundle aSavedInstanceState) {
        super.onCreate(aSavedInstanceState);
        setContentView(R.layout.activity_main);

        GraphView myGraph = findViewById(R.id.graph);
        theSeries = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 7),
                new DataPoint(1, 7),
                new DataPoint(2, 8),
                new DataPoint(3, 6),
                new DataPoint(4, 9)
        });
        myGraph.addSeries(theSeries);
        myGraph.getViewport().setScrollable(true);
        myGraph.getViewport().setScalable(true);
    }

    public void onClick(View aView) {
        EditText myXCoordinate = findViewById(R.id.text_X);
        EditText myYCoordinate = findViewById(R.id.text_Y);
        String myXString = myXCoordinate.getText().toString();
        String myYString = myYCoordinate.getText().toString();
        float myXValue = myXString.isEmpty() ? Float.NaN : Float.parseFloat(myXString);
        float myYValue = myYString.isEmpty() ? Float.NaN : Float.parseFloat(myYString);
        theSeries.appendData(new DataPoint(myXValue, myYValue), true, 50);
        GraphView myGraph = findViewById(R.id.graph);
    }

    public void onClear(View aView) {
        theSeries.resetData(new DataPoint[] {new DataPoint(0,1)});
    }
}
