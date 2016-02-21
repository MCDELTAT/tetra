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
    private LineGraphSeries<DataPoint> series;


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
        series = new LineGraphSeries<DataPoint>();
        graph.addSeries(series);

        ///sdcard/Download/GBSwater_with_Aqui_and_Cren_for_AppTeam_160126.csv
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

        PointsGraphSeries<DataPoint> series2 = new PointsGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(250,250),
                new DataPoint(0,0),
                //Can't b/c the point is a double, not int
                // new DataPoint(getDimensions.dataArray.get(1).dim1)
        });
        graph.addSeries(series2);
        series2.setShape(PointsGraphSeries.Shape.POINT);
    }
}
