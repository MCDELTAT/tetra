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

    // Datamanager class to deal with file IO

    DataManager dataFile = new DataManager();

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

        //-------Add the first element to my names array, this assumes that the file is not empty----------
        namesArray.add(dataFile.dataArray.get(0).organism);
        //-------Go through the data, find and add organisms that are not in the names array ----------
        for (int i = 1; i < dataFile.dataArray.size(); i++) {
            if (!namesArray.contains(dataFile.dataArray.get(i).organism)){
                namesArray.add(dataFile.dataArray.get(i).organism);
            }
        }
        //-----Prints out our names array, primary use for debugging ----
        System.out.println(namesArray.size());
        for (int i = 0; i < namesArray.size(); i++){
            System.out.println(namesArray.get(i));
        }

        //------Used when we generate the random colors.
        Random randomNum = new Random();

        GraphView graph = (GraphView) findViewById(R.id.graph);
        //data

        for(int i=0;i<namesArray.size();i++) {
            //-------------------Generate points that only have the organism name of names array of i
            series = new PointsGraphSeries<DataPoint>(generateData(namesArray.get(i)));
            graph.addSeries(series);
            series.setShape(PointsGraphSeries.Shape.POINT);
            series.setSize(3f);
            //----------------Set a random color for that organism
            series.setColor(Color.rgb(randomNum.nextInt(), randomNum.nextInt(), randomNum.nextInt()));

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
    private DataPoint[] generateData(String target) {
        int count = dataFile.dataArray.size();
        //------------------Made an temp array list for the points
        ArrayList<DataPoint> temp = new ArrayList();
        for (int i = 0; i < count; i++) {
            //-------------This adds a point only if its the organism we want
            if ( target.equals(dataFile.dataArray.get(i).organism)) {
                double xValue = (int) dataFile.dataArray.get(i).dim1;
                double yValue = (int) dataFile.dataArray.get(i).dim2;
                DataPoint v = new DataPoint(xValue, yValue);
                temp.add(v);
            }
        }
        //-------------------This converts our Array list to a normal array
        DataPoint[] values = new DataPoint[temp.size()];
        temp.toArray(values);

        return values;
    }
}