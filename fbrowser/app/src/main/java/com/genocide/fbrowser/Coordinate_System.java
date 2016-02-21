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
        series.setShape(PointsGraphSeries.Shape.POINT);
        series.setSize(2);
        //series.setColor(Color.BLUE); // sets color of series

        // lets you tap on point to get point info, not sure if you can modify to display other info
        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                int count = dataFile.dataArray.size();
                int index= 0;
                for(int i = 0; i < count; i++){
                    double xValue = (int)dataFile.dataArray.get(i).dim1;
                    double yValue = (int)dataFile.dataArray.get(i).dim2;
                    DataPoint v = new DataPoint(xValue, yValue);
                    if (v.getX()== dataPoint.getX() && v.getY() == dataPoint.getY()){
                        System.out.println(dataPoint);
                        index = i;
                        break;
                    }
                }
                System.out.println("Index: " + index);
                Toast.makeText(Coordinate_System.this,
                        "Contig: " +
                        (dataFile.dataArray.get(index).contig)+
                        "\nOrganism: " +
                        (dataFile.dataArray.get(index).organism)+
                        "\nSize: " +
                        (dataFile.dataArray.get(index).size)+
                        "\ndim1: " +
                        (dataFile.dataArray.get(index).dim1)+
                        "\ndim2: " +
                        (dataFile.dataArray.get(index).dim2)+
                        "\ndim3: " +
                        (dataFile.dataArray.get(index).dim3), Toast.LENGTH_SHORT).show();
            }
        });

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
/*
        GraphView point_graph = (GraphView) findViewById(R.id.graph);
        PointsGraphSeries<DataPoint> point_series = new PointsGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 5),
                new DataPoint(1, 0),
                new DataPoint(2, 3),
                new DataPoint(3, 3),
                new DataPoint(4, 1.5)
        });
        point_graph.addSeries(point_series);
        point_series.setShape(PointsGraphSeries.Shape.POINT);
        point_series.setColor(Color.BLUE);
        point_series.setSize(18);
        point_series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(Coordinate_System.this, "Series1: On Data Point clicked: " + dataPoint, Toast.LENGTH_SHORT).show();
            }
        });
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
