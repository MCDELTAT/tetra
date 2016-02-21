package com.genocide.fbrowser;


import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.View;


import android.util.AttributeSet;
import android.view.View;
import android.view.LayoutInflater;
import android.os.Handler;


import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;


import android.os.Bundle;

import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Random;

public class Coordinate_System extends AppCompatActivity {

    //--------------------------------------
    //retrieving DataArray from DataManager
    //--------------------------------------
    private PointsGraphSeries<DataPoint> series;


    //String receiveLoc = (String) getIntent().getExtras().get("fileLocation");
    DataManager dataFile = new DataManager();

    //dataFile.dataParser()
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String Loc = intent.getStringExtra("fileLocation");
        dataFile.dataParser(Loc);

        System.out.println("Hello " + Loc);
        setContentView(R.layout.activity_coordinate_system);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        GraphView graph = (GraphView) findViewById(R.id.graph);
        //data
        series = new PointsGraphSeries<DataPoint>(generateData());
        graph.addSeries(series);

        double xPos = dataFile.dim1Max;
        System.out.println("READ MYU XPOS: "+ xPos);
        double xNeg = dataFile.dim1Min;
        double yPos = dataFile.dim2Max;
        double yNeg = dataFile.dim2Min;

        //viewports to set graph
        Viewport viewport = graph.getViewport();

        viewport.setXAxisBoundsManual(true);
        viewport.setMinX(xNeg);
        viewport.setMaxX(xPos);

        viewport.setYAxisBoundsManual(true);
        viewport.setMinY(yNeg);
        viewport.setMaxY(yPos);

        viewport.setScrollable(true);
        int i = (int)dataFile.dataArray.get(1).dim1;
        int z = (int)dataFile.dataArray.get(1).dim2;
        /*
        PointsGraphSeries<DataPoint> series2 = new PointsGraphSeries<DataPoint>(
                generateData()
        );
        graph.addSeries(series2);
        series2.setShape(PointsGraphSeries.Shape.POINT);
        */
    }
    private DataPoint[] generateData(){
        int count = dataFile.dataArray.size();
        DataPoint[] values = new DataPoint[count];
        for(int i = 0; i < count; i++){
            double xValue = (int)dataFile.dataArray.get(i).dim1;
            double yValue = (int)dataFile.dataArray.get(i).dim2;
            DataPoint v = new DataPoint(xValue, yValue);
            values[i] = v;
        }
        return values;
    }
}
