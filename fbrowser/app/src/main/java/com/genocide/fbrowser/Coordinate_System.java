package com.genocide.fbrowser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

    private PointsGraphSeries<DataPoint> contigSeries;

    //Obtain file Location from FileBrowser.java
    Bundle receiveLoc = getIntent().getExtras();
    String fileLocation = (String) receiveLoc.get("fileLocation");
    //String receiveLoc = (String) getIntent().getExtras().get("fileLocation");
    DataManager dataFile = new DataManager(fileLocation);

    //dataFile.dataParser()


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GraphView graph = (GraphView) findViewById(R.id.graph);

        setContentView(R.layout.activity_coordinate__system);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Send data file to dataParser then access the array
        dataFile.dataParser();
        //GraphView graph = (GraphView) findViewById(R.id.graph);
        //data

        contigSeries = new PointsGraphSeries<>(generateData());
        graph.addSeries(contigSeries);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(dataFile.dim1Min - 10);
        graph.getViewport().setMaxX(dataFile.dim1Max + 10);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(dataFile.dim2Min - 10);
        graph.getViewport().setMaxY(dataFile.dim2Max + 10);

        graph.getViewport().setScrollable(true);

    }

    private DataPoint[] generateData(){
        int count = dataFile.dataArray.size();
        DataPoint[] values = new DataPoint[count];
        for(int i = 0; i < count; i++){
            double xValue = dataFile.dataArray.get(i).dim1;
            double yValue = dataFile.dataArray.get(i).dim2;

            DataPoint v = new DataPoint(xValue, yValue);
            values[i] = v;
        }
        return values;
    }
}

