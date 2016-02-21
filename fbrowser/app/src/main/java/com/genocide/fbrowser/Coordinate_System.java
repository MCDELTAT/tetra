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

public class Coordinate_System extends Activity {

    //--------------------------------------
    //retrieving DataArray from DataManager
    //--------------------------------------

    private PointsGraphSeries<DataPoint> contigSeries;


    //String receiveLoc = (String) getIntent().getExtras().get("fileLocation");
    DataManager dataFile = new DataManager();

    //dataFile.dataParser()


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Obtain file Location from FileBrowser.java
        Intent startingIntent = getIntent();
        String Loc = startingIntent.getStringExtra("fileLocation");
        dataFile.dataParser(Loc);

        /*
        Bundle receiveLoc = getIntent().getExtras();
        String fileLocation = (String) receiveLoc.get("fileLocation");
        */
        GraphView graph = (GraphView) findViewById(R.id.graph);
        System.out.println("IT WORKED " + Loc);
        setContentView(R.layout.activity_coordinate__system);
        System.out.println("0");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        System.out.println("01");
        //setSupportActionBar(toolbar);

        //Send data file to dataParser then access the array
        System.out.println("1");
        //GraphView graph = (GraphView) findViewById(R.id.graph);
        //data

        contigSeries = new PointsGraphSeries<>(generateData());

        System.out.println("12");
        //graph.addSeries(contigSeries);
        System.out.println("123");
        
        /*
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(dataFile.dim1Min - 10);
        graph.getViewport().setMaxX(dataFile.dim1Max + 10);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(dataFile.dim2Min - 10);
        graph.getViewport().setMaxY(dataFile.dim2Max + 10);

        graph.getViewport().setScrollable(true);
        */

    }

    private DataPoint[] generateData(){
        int count = dataFile.dataArray.size();
        DataPoint[] values = new DataPoint[count];
        for(int i = 0; i < count; i++){
            double xValue = dataFile.dataArray.get(i).dim1;
            double yValue = dataFile.dataArray.get(i).dim2;

            DataPoint v = new DataPoint(xValue, yValue);
            System.out.println(v);
            values[i] = v;
        }
        return values;
    }
}

