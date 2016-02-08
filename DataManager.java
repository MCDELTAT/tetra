//package com.juscam.parse;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.*;
import java.io.IOException;
import java.util.*;
import com.opencsv.CSVReader;
//in the DataManger.java file
public class DataManager {
		// min and max for each dim to be calculated 
		// everytime i push data to an object
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
		// could move this to runCvsParser 
	}

	public void dataParser() {


		DataObject particle1 = new DataObject("2087795435_Aqui_GBSWBa_contig02328_1","Aqui",2000,-152.71487,198.21319,25.004952);
  	
  	DataObject particle2 = new DataObject("2087695435_Aqui_GBSWBa_contig02328_1","Aqui",3000,-151.71487,188.21319,24.004952);

  	// placeholder until I get the csv libary working
  	// at this poin I would also check if there is a new min and max for each dim everytime I create an object


		CSVReader reader = new CSVReader(new FileReader("test.csv"));
    String [] nextLine;
    while ((nextLine = reader.readNext()) != null) {
      // nextLine[] is an array of values from the line
      System.out.println(nextLine[0] + nextLine[1] + "etc...");
    }


  	dataArray.add(particle1); // example when done parsing a line and DataObject is created, push to array
  	dataArray.add(particle2);


	}
	/*
	public void runCvsParser() {
	
    }
    */
	 // why the ? ;



}



// use opencsv 


/*
		System.out.println("contig: " + particle1.contig);
  	System.out.println("organism: " + particle1.organism);
  	System.out.println("size: " + particle1.size);
  	System.out.println("dim1: " + particle1.dim1);
  	System.out.println("dim2: " + particle1.dim2);
  	System.out.println("dim3: " + particle1.dim3);
  	*/


/*
		double test = -152.71487;

  	if (particle1.dim1 != test) System.out.println("Fail!");

*/