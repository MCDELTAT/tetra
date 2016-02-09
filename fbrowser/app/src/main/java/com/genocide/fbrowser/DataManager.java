package com.genocide.fbrowser;

import java.util.*; // for ArrayList
import java.io.*;


//in the DataManger.java file
public class DataManager {
    private String csvLocation;

    public double dim1Max;
    public double dim1Min;

    public double dim2Max;
    public double dim2Min;

    public double dim3Max;
    public double dim3Min;

    public ArrayList<DataObject> dataArray = new ArrayList<DataObject>();

    public DataManager(String fileLocation) {
        csvLocation = fileLocation;
    }

    public void dataParser() {
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(csvLocation);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

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

                // check for min and max for each dim
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
            System.out.println("Unable to open file '" + csvLocation + "'");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + csvLocation + "'");
        }
    }
}

