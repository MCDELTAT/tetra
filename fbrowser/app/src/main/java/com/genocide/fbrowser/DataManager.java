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
    // ArrayList that each data object is added to

    public DataManager(String loc) {
        csvLocation = loc;
    }

    private static int commaCount(String line) {
        int commas = 0;
        for(int i = 0; i < line.length(); i++) {
            if(line.charAt(i) == ',') commas++;
        }
        return commas;
    }
    public void dataParser() {
        // splits filelocation
        String[] fileLoc = csvLocation.split(":");
        String tempLoc = "";
        System.out.println(fileLoc.length);

        if (fileLoc.length == 2){
            tempLoc = "/sdcard/" + fileLoc[1];
        }
        else {
            tempLoc = fileLoc[0];
        }

        Log.d("indataParser", "dataParser: ");
        BufferedReader br = null;
        String line = "";
        try {
            br = new BufferedReader(new FileReader(tempLoc));
            line = br.readLine();

            while ((line = br.readLine()) != null) {
                // line comma count function
                int commas = commaCount(line);
                System.out.println(line + " has " + commas + " commas!");
                // sanity check for file object line integrity, error if 5
                // commas not found
                if (commas != 5) {
                    // clears array as csv file not in valid format
                    dataArray.clear();
                    System.out.println("Data in wrong format");
                    break;
                }
                // each data point is read line by line
                String[] splitLine = line.split(",");
                // input is received as a string so I convert for later uses
                int size = Integer.parseInt(splitLine[2]);
                double dim1 = Double.parseDouble(splitLine[3]);
                double dim2 = Double.parseDouble(splitLine[4]);
                double dim3 = Double.parseDouble(splitLine[5]);

                // DataObject inputs as follows (contig, organism, size, dim1, dim2, dim3)
                DataObject particle = new DataObject(splitLine[0], splitLine[1], size, dim1, dim2, dim3);

                // if first run through for min max so it has something to compare with
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
                /*
                System.out.println("dim1max: "+ dim1Max);
                System.out.println("dim2max: "+ dim2Max);
                System.out.println("dim3max: "+ dim3Max);
                System.out.println("dim1min: "+ dim1Min);
                System.out.println("dim2min: "+ dim2Min);
                System.out.println("dim3min: "+ dim3Min);
                */

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
          } catch (NumberFormatException e) {
            System.out.println("Data not in right format, please provide a valid csv file");
            // clears array after a formatting error
            dataArray.clear();
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            System.out.println("Use a Samsung device using internal mem, or install a file manager");
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
