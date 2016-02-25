package com.genocide.fbrowser;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.Series;
import java.util.ArrayList;
import java.util.Random;

public class Coordinate_System extends AppCompatActivity {

    //--------------------------------------
    //retrieving DataArray from DataManager
    //--------------------------------------
    private PointsGraphSeries<DataPoint> series;

    //Used to keep the different types of organisms
    public ArrayList<String> namesArray = new ArrayList();
    public ArrayList<Integer>colorArray = new ArrayList();

    // Datamanager class to deal with file IO

    DataManager dataFile = new DataManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String Loc = intent.getStringExtra("fileLocation");
        dataFile.dataParser(Loc);

        System.out.println("File Location: " + Loc);
        setContentView(R.layout.activity_coordinate_system);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //------Used when we generate the random colors.
        Random randomNum = new Random();

        //-------Add the first element to my names array, this assumes that the file is not empty----------
        namesArray.add(dataFile.dataArray.get(0).organism);
        colorArray.add(Color.rgb(randomNum.nextInt(), randomNum.nextInt(), randomNum.nextInt()));
        //-------Go through the data, find and add organisms that are not in the names array ----------
        for (int i = 1; i < dataFile.dataArray.size(); i++) {
            if (!namesArray.contains(dataFile.dataArray.get(i).organism)){
                namesArray.add(dataFile.dataArray.get(i).organism);
                colorArray.add(Color.rgb(randomNum.nextInt(), randomNum.nextInt(), randomNum.nextInt()));
            }
        }
        //-----Prints out our names array, primary use for debugging ----
        System.out.println(namesArray.size());
        for (int i = 0; i < namesArray.size(); i++){
            System.out.println(namesArray.get(i));
        }

        GraphView graph = (GraphView) findViewById(R.id.graph);
        //data

        //for(int i=0;i<namesArray.size();i++) {
        for(int i = 0; i < dataFile.dataArray.size(); i++) {
            double xValue = (int) dataFile.dataArray.get(i).dim1;
            double yValue = (int) dataFile.dataArray.get(i).dim2;
            DataPoint v = new DataPoint(xValue, yValue);
            DataPoint[] XYarray = {v};
            //-------------------Generate points that only have the organism name of names array of i

            series = new PointsGraphSeries<DataPoint>(XYarray);
            graph.addSeries(series);
            series.setShape(PointsGraphSeries.Shape.POINT);
            series.setSize(5);




            //----------------Set a color for that organism
            int color = colorArray.get(0);
            // sets color for each point
            for(int x = 0; x < namesArray.size(); ++x){
                if(dataFile.dataArray.get(i).organism.toString().equals(namesArray.get(x).toString())){
                    color = colorArray.get(x);
                    break;
                }
            }
            series.setColor(color);

            //----!-!-!-!-!I moved this here because the tap would only work for the last series graphed, in my case the unknown's
            series.setOnDataPointTapListener(new OnDataPointTapListener() {
                @Override
                public void onTap(Series series, DataPointInterface dataPoint) {
                    int count = dataFile.dataArray.size();
                    int index = 0;
                    for (int i = 0; i < count; i++) {
                        double xValue = (int) dataFile.dataArray.get(i).dim1;
                        double yValue = (int) dataFile.dataArray.get(i).dim2;
                        DataPoint v = new DataPoint(xValue, yValue);
                        if (v.getX() == dataPoint.getX() && v.getY() == dataPoint.getY()) {
                            System.out.println(dataPoint);
                            index = i;
                            break;
                        }
                    }
                    System.out.println("Index: " + index);
                    Toast.makeText(Coordinate_System.this,
                            "Contig: " +
                                    (dataFile.dataArray.get(index).contig) +
                                    "\nOrganism: " +
                                    (dataFile.dataArray.get(index).organism) +
                                    "\nSize: " +
                                    (dataFile.dataArray.get(index).size) +
                                    "\ndim1: " +
                                    (dataFile.dataArray.get(index).dim1) +
                                    "\ndim2: " +
                                    (dataFile.dataArray.get(index).dim2) +
                                    "\ndim3: " +
                                    (dataFile.dataArray.get(index).dim3), Toast.LENGTH_SHORT).show();
                }
            });
        }

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

        viewport.setScalable(true);
        viewport.setScrollable(true);

    }
    //-------------------Added a string input to generate points for that organism only
}