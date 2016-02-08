package com.genocide.fbrowser;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;


/**
 * Created by Justin on 2/7/2016.
 */
public class DataManager {
    // min and max for each dim to be calculated
    // everytime i push data to an object
    private BufferedReader bufferedObject;
    BufferedReader br = null;



    public double dim1Max;
    public double dim1Min;

    public double dim2Max;
    public double dim2Min;

    public double dim3Max;
    public double dim3Min;

    public ArrayList<DataObject> dataArray = new ArrayList<DataObject>();

    public DataManager(BufferedReader fileLocation) {
        BufferedReader bufferedObject = fileLocation;
        // could move this to runCvsParser
    }

    public void dataParser() {
        String line = null;

        try {
            BufferedReader bufferedReader = bufferedObject;

            // Skips the first line
            bufferedReader.readLine();

            while((line = bufferedReader.readLine()) != null) {

                List<String> DataList = Arrays.asList(line.split(","));

                // check for min and max for each dim
                int size = Integer.parseInt(DataList.get(2));

                // convert from string to respective variable

                double dim1 = Double.parseDouble(DataList.get(3));
                double dim2 = Double.parseDouble(DataList.get(4));
                double dim3 = Double.parseDouble(DataList.get(5));

                DataObject particle = new DataObject(DataList.get(0),DataList.get(1),size,dim1,dim2,dim3);

                dataArray.add(particle);

                // check for min and max
                if(dim1Max < dim1){
                    dim1Max = dim1;
                }
                if(dim1Min > dim1){
                    dim1Min = dim1;
                }

                if(dim2Max < dim2){
                    dim2Max = dim2;
                }
                if(dim2Min > dim2){
                    dim2Min = dim2;
                }


                if(dim3Max < dim3){
                    dim3Max = dim3;
                }
                if(dim3Min > dim3){
                    dim3Min = dim3;
                }

            }
            // Close file.
            bufferedReader.close();
        }

        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + bufferedObject + "'");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + bufferedObject + "'");
        }
    }
}



