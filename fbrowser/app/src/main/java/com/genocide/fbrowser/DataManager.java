package com.genocide.fbrowser;
<<<<<<< HEAD

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

=======
//package com.juscam.parse;
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
    //public DataManager(ArrayList<String> list) {
        //csvLocation = fileLocation;
    //}

    public void dataParser(String loc) {
        Log.d("indataParser", "dataParser: ");

        //Toast.makeText(FileBrowser.this, loc, Toast.LENGTH_LONG).show();
        BufferedReader br = null;
        String line = "";
        //ArrayList<String> contig = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(loc));
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                //contig.add(line);

                System.out.println(line);
                // System.out.println("contig" + contig[1]);
                //Toast.makeText(FileBrowser.this, contig[3], Toast.LENGTH_SHORT).show();
                String[] line2 = line.split(",");
                //line.split(",");
                int size = Integer.parseInt(line2[2]);
                double dim1 = Double.parseDouble(line2[3]);
                double dim2 = Double.parseDouble(line2[4]);
                double dim3 = Double.parseDouble(line2[5]);
                DataObject particle = new DataObject(line2[0], line2[2], size, dim1, dim2, dim3);
                dataArray.add(particle);
                //System.out.println("contig" + line[1]);
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
        System.out.println("dim1max: "+ dim1Max);
        System.out.println("dim2max: "+ dim2Max);
        System.out.println("dim3max: "+ dim3Max);
        System.out.println("dim1min: "+ dim1Min);
        System.out.println("dim2min: "+ dim2Min);
        System.out.println("dim3min: "+ dim3Min);
    }
}
>>>>>>> refs/remotes/origin/moseleyBranch
