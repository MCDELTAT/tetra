package com.genocide.fbrowser;


import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CUSTOM_DIALOG_ID:
                if (resultCode == RESULT_OK) {
                    path = data.getData().getPath();

                    String loc = path.toString();

<<<<<<< HEAD
                    Toast.makeText(FileBrowser.this, loc, Toast.LENGTH_LONG).show();
=======
                    //Toast.makeText(FileBrowser.this, loc, Toast.LENGTH_LONG).show();
>>>>>>> refs/remotes/origin/moseleyBranch

                    DataManager dataFile = new DataManager(loc);
                    // I pass directory location to my class above and parse it using the command
                    // below
                    dataFile.dataParser();
<<<<<<< HEAD

=======
                    Toast.makeText(FileBrowser.this,"test:"+dataFile.dim1Max,Toast.LENGTH_LONG);

                }
        }
    }
}

*/


@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    switch (requestCode) {
        case CUSTOM_DIALOG_ID:
            if (resultCode == RESULT_OK) {
                path = data.getData().getPath();
                String loc = path.toString();
                DataManager dataFile = new DataManager();
                dataFile.dataParser(loc);
                }

            break;

    }
}
>>>>>>> refs/remotes/origin/moseleyBranch

                }
        }
    }

}




