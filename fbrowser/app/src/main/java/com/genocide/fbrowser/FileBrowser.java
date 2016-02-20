package com.genocide.fbrowser;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class FileBrowser extends AppCompatActivity {
    Button buttonOpenFileButton;
    String path;
    Button buttonGraph;
    static final int CUSTOM_DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_file_browser);
        //implemtes button
        buttonOpenFileButton = (Button) findViewById(R.id.openFileButton);
        buttonOpenFileButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openFolder(v);
            }
        });

    }

    public void openFolder(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        //intent for Samsung file manager
        Intent sIntent = new Intent("com.sec.android.app.myfiles.PICK_DATA");
        sIntent.putExtra("CONTENT_TYPE", "*/*");
        sIntent.addCategory(Intent.CATEGORY_DEFAULT);

        Intent chooserIntent;
        if (getPackageManager().resolveActivity(sIntent, 0) != null) {
            // it is device with samsung file manager
            chooserIntent = Intent.createChooser(sIntent, "Open file");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{intent});
        } else {
            chooserIntent = Intent.createChooser(intent, "Open file");
        }

        try {
            startActivityForResult(chooserIntent, CUSTOM_DIALOG_ID);

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(), "No File Manager found.", Toast.LENGTH_SHORT).show();
        }
    }

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    switch (requestCode) {
        case CUSTOM_DIALOG_ID:
            if (resultCode == RESULT_OK) {
                path = data.getData().getPath();
                String loc = path.toString();

                // DataManager class to open and parse csv
                DataManager dataFile = new DataManager(loc);

                // passing on location to parse data
                dataFile.dataParser();

                // test file output
                System.out.println("File Location: " + loc);

                System.out.println("Number of data points:" + dataFile.dataArray.size());

                System.out.println("Data Point: ");

                // examples how to access data of an object, I print all the data points here
                // I'll leave this here for tests and disable when prototype is ready
                for(int i = 0; i < dataFile.dataArray.size(); i++){

                    System.out.println("Data Point #: " + Integer.toString(i + 1));
                    System.out.println(dataFile.dataArray.get(i).contig);
                    System.out.println(dataFile.dataArray.get(i).organism);
                    System.out.println(dataFile.dataArray.get(i).size);
                    System.out.println(dataFile.dataArray.get(i).dim1);
                    System.out.println(dataFile.dataArray.get(i).dim2);
                    System.out.println(dataFile.dataArray.get(i).dim3);
                }
                //send location of file to Coordinate_Systemen.java and start activity
                Intent sendLoc = new Intent(FileBrowser.this, Coordinate_System.class);
                sendLoc.putExtra("fileLocation", loc);
                startActivity(sendLoc);

            }
            break;
    }
}


}

