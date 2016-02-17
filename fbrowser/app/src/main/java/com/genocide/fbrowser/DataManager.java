package com.genocide.fbrowser;

import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DataManager {
    private String csvLocation;
    public double dim1Max;
    public double dim1Min;
    public double dim2Max;
    public double dim2Min;
    public double dim3Max;
    public double dim3Min;
    public ArrayList<DataObject> dataArray = new ArrayList();
    // ArrayList each data object is added to

    public DataManager(String loc) {
        csvLocation = loc;
    }

    public void dataParser() {
        // sets file location to csvLocation
        //csvLocation = loc;
        System.out.println(csvLocation);
        Log.d("indataParser", "dataParser: ");
        BufferedReader br = null;
        String line = "";
        try {
            // please comment if uncommented below.
            //loc = "/sdcard/Download/GBSwater_with_Aqui_and_Cren_for_AppTeam_160126.csv"; // testing
            // comment this above after, used for quick testing instead above having to go to
            // the specific directory every time
            br = new BufferedReader(new FileReader(csvLocation));
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                // each data point is read line by line
                String[] splitLine = line.split(",");
                // input is received as a string so I convert for later uses
                int size = Integer.parseInt(splitLine[2]);
                double dim1 = Double.parseDouble(splitLine[3]);
                double dim2 = Double.parseDouble(splitLine[4]);
                double dim3 = Double.parseDouble(splitLine[5]);

                // DataObject inputs as follows (contig, organism, size, dim1, dim2, dim3)
                DataObject particle = new DataObject(splitLine[0], splitLine[1], size, dim1, dim2, dim3);

                // if first run through for min max
                if(dataArray.size() == 0) {
                    dim1Max = dim1;
                    dim1Min = dim1;
                    dim2Max = dim2;
                    dim2Min = dim2;
                    dim3Max = dim3;
                    dim3Min = dim3;
                }
                // pushes object, with line data split into, an arraylist.
                dataArray.add(particle);

                // visualizing min max
                // comment out when opengl is up and running
                System.out.println("dim1max: "+ dim1Max);
                System.out.println("dim2max: "+ dim2Max);
                System.out.println("dim3max: "+ dim3Max);
                System.out.println("dim1min: "+ dim1Min);
                System.out.println("dim2min: "+ dim2Min);
                System.out.println("dim3min: "+ dim3Min);

                // checks for dim min max every line
                if (dim1Max < dim1) {
                    dim1Max = dim1;
                }
                if (dim1Min > dim1) {
                    dim1Min = dim1;
                }

                if (dim2Max < dim2) {
                    dim2Max = dim2;
                }
                if (dim2Min > dim2) {
                    dim2Min = dim2;
                }

                if (dim3Max < dim3) {
                    dim3Max = dim3;
                }
                if (dim3Min > dim3) {
                    dim3Min = dim3;
                }
            }
          } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
