package com.genocide.fbrowser;

import android.content.Intent;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class FileBrowser extends AppCompatActivity {
    Button buttonOpenFileButton;
    String path;

    static final int CUSTOM_DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_file_browser);
        //implements button
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
        intent.setType("text/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        //intent for Samsung file manager
        Intent sIntent = new Intent("com.sec.android.app.myfiles.PICK_DATA");
        sIntent.putExtra("CONTENT_TYPE", "text/*");
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
                    DataManager dataFile = new DataManager();
                    // passing on location to parse data
                    dataFile.dataParser(loc);
                    // debug data
                    System.out.println("Number of data points:" + dataFile.dataArray.size());
                    if(dataFile.dataArray.size() > 0){
                        Intent sendLoc = new Intent(FileBrowser.this, Coordinate_System.class);
                        sendLoc.putExtra("fileLocation", loc);
                        startActivity(sendLoc);
                    }
                    else {
                        Toast.makeText(FileBrowser.this,
                                "Load a valid .CSV file"
                                , Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}